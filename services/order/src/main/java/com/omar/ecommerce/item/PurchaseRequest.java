package com.omar.ecommerce.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Item is mandatory")
        Integer itemId,
        @Positive(message = "Quantity is mandatory")
        double quantity
) {
}
