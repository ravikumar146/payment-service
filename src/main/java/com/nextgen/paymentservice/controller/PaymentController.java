package com.nextgen.paymentservice.controller;

import com.nextgen.paymentservice.dto.CreateOrderRequest;
import com.nextgen.paymentservice.dto.CreateOrderResponse;
import com.nextgen.paymentservice.dto.VerifyPaymentRequest;
import com.nextgen.paymentservice.service.PaymentService;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Payments", description = "APIs for managing payments")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request)
            throws RazorpayException {
        CreateOrderResponse response = paymentService.createOrder(request.amount());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "To verify the payment")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentRequest request) throws RazorpayException {

        boolean verified = paymentService.verifyPayment(request.razorpayOrderId(),
                request.razorpayPaymentId(), request.razorpaySignature());
        if (verified) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Payment verified successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Payment verification failed"));
    }

}