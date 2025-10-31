package com.swapnil.Service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.swapnil.Domain.PaymentMethod;
import com.swapnil.modal.PaymentOrder;
import com.swapnil.payloadreponse.PaymentLinkResponse;
import com.swapnil.payloadreponse.payloaddto.BookingDTO;
import com.swapnil.payloadreponse.payloaddto.UserDTO;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO bookingDTO, PaymentMethod paymentMethod) throws RazorpayException;

    PaymentOrder getPaymentOrderId(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws Exception;
}
