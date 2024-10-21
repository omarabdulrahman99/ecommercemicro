package com.omar.ecommerce.customer;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void testToCustomer() {
        CustomerRequest request = new CustomerRequest(null, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));


        Customer customer = customerMapper.toCustomer(request);

        assertThat(customer).isNotNull();
        assertThat(customer.getFirstname()).isEqualTo("John");
        assertThat(customer.getLastname()).isEqualTo("Doe");
        assertThat(customer.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(customer.getAddress()).isEqualTo(request.address());
    }

    @Test
    void testFromCustomer() {
        Address address = new Address("123 Main St", "1A", "12345");
        Customer customer = Customer.builder()
                .id("1")
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .address(address)
                .build();

        CustomerResponse response = customerMapper.fromCustomer(customer);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo("1");
        assertThat(response.firstname()).isEqualTo("John");
        assertThat(response.lastname()).isEqualTo("Doe");
        assertThat(response.email()).isEqualTo("john.doe@example.com");
        assertThat(response.address()).isEqualTo(address);
    }

    @Test
    void testToCustomer_NullRequest() {
        Customer customer = customerMapper.toCustomer(null);
        assertThat(customer).isNull();
    }
}
