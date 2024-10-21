package com.omar.ecommerce.item;

import jakarta.validation.constraints.NotNull;

public record ItemPurchaseRequest(
        @NotNull(message = "Item ID is required")
        Integer itemId,
        @NotNull(message = "Quantity must be specified")
        double quantity,
        @NotNull(message = "SKU is required")
        String sku // Optional, if you want to include SKU in the request
) {
}
