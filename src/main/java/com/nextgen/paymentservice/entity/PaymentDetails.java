package com.nextgen.paymentservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
        name = "Payment Details",
        description = "Tells about the payment details in the system."
)
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String razorpayOrderId;

    @Column(nullable = false, length = 150)
    private String razorpayPaymentId;
}
