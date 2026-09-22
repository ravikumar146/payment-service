package com.nextgen.paymentservice.service;

import com.nextgen.paymentservice.dto.CreateOrderResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    CreateOrderResponse createOrder(long amount) throws RazorpayException;

    boolean verifyPayment(String orderId, String paymentId, String signature) throws RazorpayException;
}
