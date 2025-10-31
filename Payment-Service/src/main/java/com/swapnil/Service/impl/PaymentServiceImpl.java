package com.swapnil.Service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import com.swapnil.Domain.PaymentMethod;
import com.swapnil.Domain.PaymentOrderSatus;
import com.swapnil.Service.PaymentService;
import com.swapnil.modal.PaymentOrder;
import com.swapnil.payloadreponse.PaymentLinkResponse;
import com.swapnil.payloadreponse.payloaddto.BookingDTO;
import com.swapnil.payloadreponse.payloaddto.UserDTO;
import com.swapnil.repository.PaymentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecret;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpaySecretKey;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO bookingDTO, PaymentMethod paymentMethod) throws RazorpayException {
        Long amount = bookingDTO.getTotalServices() * 100L;

        PaymentOrder order = new PaymentOrder();
        order.setAmount(String.valueOf(amount));
        order.setPaymentmethood(paymentMethod);
        order.setSaloonId(String.valueOf(bookingDTO.getSaloonId()));
        order.setBookingId(bookingDTO.getId());
        order.setUserId(user.getId());

        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink payment = createRazorpayPaymentLink(user, amount, savedOrder.getId());
            if (payment != null) {
                paymentLinkResponse.setPaymentLink_URL(payment.get("short_url"));
                paymentLinkResponse.setPaymentLink_Id(payment.get("id"));
                savedOrder.setPaymentLinkId(payment.get("id"));
            }
        } else if (paymentMethod.equals(PaymentMethod.STRIPE)) {
            String paymentUrl = createStripePaymentLink(user, amount, savedOrder.getId());
            if (paymentUrl != null) {
                paymentLinkResponse.setPaymentLink_URL(paymentUrl);
                savedOrder.setPaymentLinkId("STRIPE_" + savedOrder.getId());
            }
        }

        paymentOrderRepository.save(savedOrder);
        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderId(Long id) throws Exception {
        return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("Payment Order Not Found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpaySecretKey, razorpayApiKey);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullname());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);
        paymentLinkRequest.put("reminder_enable", true);
        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/" + orderId);
        paymentLinkRequest.put("callback_method", "get");

        return razorpayClient.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {
        Stripe.apiKey = stripeSecret;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("INR")
                                .setUnitAmount(amount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Saloon Appointment Booking").build()
                                ).build()
                        ).build()
                ).build();

        try {
            return com.stripe.model.checkout.Session.create(params).getUrl();
        } catch (Exception e) {
            logger.error("Error while creating Stripe payment link", e);
            return null;
        }
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {
        if (paymentOrder.getStatus().equals(PaymentOrderSatus.PENDING)) {
            if (paymentOrder.getPaymentmethood().equals(PaymentMethod.RAZORPAY)) {
                RazorpayClient razorpayClient = new RazorpayClient(razorpaySecretKey, razorpayApiKey);
                Payment payment = razorpayClient.payments.fetch(paymentId);
                if (payment.get("status").equals("captured")) {
                    paymentOrder.setStatus(PaymentOrderSatus.SUCCEEDED);
                    paymentOrderRepository.save(paymentOrder);
                    return true;
                }
                return false;
            } else {
                paymentOrder.setStatus(PaymentOrderSatus.SUCCEEDED);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
        }
        return false;
    }
}
