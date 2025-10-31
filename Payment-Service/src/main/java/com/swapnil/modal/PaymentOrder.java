package com.swapnil.modal;


import com.swapnil.Domain.PaymentOrderSatus;
import com.swapnil.Domain.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long Id;

    @Column(nullable = false)
    private  String SaloonId;


    @Column(nullable = false)
    private String Amount;

    @Column(nullable = false)
    private PaymentOrderSatus status = PaymentOrderSatus.PENDING;

    @Column(nullable = false)
    private PaymentMethod paymentmethood;


    @Column(name = "payment_link_id")
    private String paymentLinkId;

    @Column(nullable = false)
    private  Long userId;

    @Column(nullable = false)
    private  Long BookingId;

}
