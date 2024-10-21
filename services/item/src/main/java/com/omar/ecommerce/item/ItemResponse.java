package com.omar.ecommerce.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ItemResponse(
        Integer id,
        String title,
        String details,
        double stockQuantity,
        BigDecimal cost,
        Integer categoryId,
        String categoryTitle,
        String categoryDetails,
        String sku,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean isActive
) {
}
