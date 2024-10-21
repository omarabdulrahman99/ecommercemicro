package com.omar.ecommerce.kafka;

import com.omar.ecommerce.customer.CustomerResponse;
import com.omar.ecommerce.order.PaymentMethod;
import com.omar.ecommerce.item.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> items

) {
}