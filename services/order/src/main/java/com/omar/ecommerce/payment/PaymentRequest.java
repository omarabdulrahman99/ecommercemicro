package com.omar.ecommerce.payment;

import com.omar.ecommerce.customer.CustomerResponse;
import com.omar.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}