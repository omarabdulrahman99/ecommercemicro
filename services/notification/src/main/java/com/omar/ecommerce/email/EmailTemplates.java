package com.omar.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    //Each time an enum constant is created, the constructor EmailTemplates(String template,
    // String subject) is called with the provided arguments
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation");

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
