package com.omar.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

//reduce clutter of empty values.
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {

}
