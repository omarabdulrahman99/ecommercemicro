package com.omar.ecommerce.item;

import java.math.BigDecimal;

public record PurchaseResponse(
    Integer itemId,
    String name,
    String description,
    BigDecimal price,
    double quantity

) {
}
