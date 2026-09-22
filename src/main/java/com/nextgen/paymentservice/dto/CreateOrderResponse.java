package com.nextgen.paymentservice.dto;

public record CreateOrderResponse(
        boolean success,
        String orderId,
        String amount,
        String currency,
        String status
) {
}
