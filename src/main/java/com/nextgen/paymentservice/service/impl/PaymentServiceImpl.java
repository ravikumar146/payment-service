package com.nextgen.paymentservice.service.impl;

import com.nextgen.paymentservice.dto.CreateOrderResponse;
import com.nextgen.paymentservice.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    String razorPaySecret;

    @Override
    public CreateOrderResponse createOrder(long amount) throws RazorpayException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt_" + System.currentTimeMillis());
        orderRequest.put("partial_payment", false);

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        return new CreateOrderResponse(
                true,
                (String) razorpayOrder.get("id"),
                String.valueOf((Object) razorpayOrder.get("amount")),
                (String) razorpayOrder.get("currency"),
                (String) razorpayOrder.get("status")
        );
    }

    public boolean verifyPayment(
            String orderId, String paymentId, String signature) throws RazorpayException {
        JSONObject attributes = new JSONObject();
        attributes.put("razorpay_order_id", orderId);
        attributes.put("razorpay_payment_id", paymentId);
        attributes.put("razorpay_signature", signature);
        return Utils.verifyPaymentSignature(attributes, razorPaySecret);
    }

}
