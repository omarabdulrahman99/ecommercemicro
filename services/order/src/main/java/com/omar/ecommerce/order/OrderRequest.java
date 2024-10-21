package com.omar.ecommerce.order;

import com.omar.ecommerce.item.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be not null")
        @NotEmpty(message = "Customer should be not empty")
        @NotBlank(message = "Customer should be not blank")
        String customerId,
        @NotEmpty(message = "You should purchase at least one item")
        List<PurchaseRequest> items

) {
}
