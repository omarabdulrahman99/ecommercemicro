package com.omar.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Item(
        Integer itemId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}