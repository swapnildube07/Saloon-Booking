package com.swapnil.controller;

import com.razorpay.RazorpayException;
import com.swapnil.Domain.PaymentMethod;
import com.swapnil.Service.PaymentService;
import com.swapnil.modal.PaymentOrder;
import com.swapnil.payloadreponse.PaymentLinkResponse;
import com.swapnil.payloadreponse.payloaddto.BookingDTO;
import com.swapnil.payloadreponse.payloaddto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO bookingDTO,
            @RequestParam PaymentMethod paymentMethod
    ) throws RazorpayException {
        UserDTO userDTO = new UserDTO();
        userDTO.setFullname("Swapnil");
        userDTO.setEmail("swapnildube07@gmail.com");
        userDTO.setId(1L);
        PaymentLinkResponse res = paymentService.createOrder(userDTO, bookingDTO, paymentMethod);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {
        PaymentOrder res = paymentService.getPaymentOrderId(paymentOrderId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/processed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws Exception {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(paymentOrder, paymentId, paymentLinkId);
        return ResponseEntity.ok(res);
    }
}
