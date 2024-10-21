package com.omar.ecommerce.orderline;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer itemId,
        double quantity
) {
}