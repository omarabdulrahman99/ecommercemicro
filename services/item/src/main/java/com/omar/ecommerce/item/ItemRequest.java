package com.omar.ecommerce.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemRequest(
        Integer id,
        @NotNull(message = "Item name is required")
        String title,
        @NotNull(message = "Item description is required")
        String details,
        @Positive(message = "Stock quantity must be positive")
        double stockQuantity,
        @Positive(message = "Cost must be positive")
        BigDecimal cost,
        @NotNull(message = "Category ID is required")
        Integer categoryId,
        @NotNull(message = "SKU is required")
        String sku
) {
}
