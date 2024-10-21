package com.omar.ecommerce.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        // Arrange
        String expectedMessage = "Customer not found";

        // Act
        CustomerNotFoundException exception = new CustomerNotFoundException(expectedMessage);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionIsThrown() {
        // Arrange
        String customerId = "123";
        String expectedMessage = String.format("No customer found with provided Id::%s", customerId);

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            throw new CustomerNotFoundException(expectedMessage);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}
