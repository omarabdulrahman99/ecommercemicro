package com.omar.ecommerce.item;

import java.math.BigDecimal;

public record ItemPurchaseResponse(
        Integer itemId,
        String title,
        String details,
        BigDecimal cost,
        double quantity,
        String sku // Adding SKU for reference
) {
}
