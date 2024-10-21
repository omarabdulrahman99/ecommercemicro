package com.omar.ecommerce.customer;

import com.omar.ecommerce.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerRequest request = new CustomerRequest(null, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        when(customerService.createCustomer(any())).thenReturn("1");

        ResponseEntity<String> response = customerController.createCustomer(request);

        assertThat(response.getBody()).isEqualTo("1");
        verify(customerService, times(1)).createCustomer(request);
    }

    @Test
    void testUpdateCustomer() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));

        ResponseEntity<Void> response = customerController.updateCustomer(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(202);
        verify(customerService, times(1)).updateCustomer(request);
    }

    @Test
    void testFindAllCustomers() {
        CustomerResponse customerResponse = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        when(customerService.findAllCustomers()).thenReturn(Arrays.asList(customerResponse));

        ResponseEntity<List<CustomerResponse>> response = customerController.findAll();

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0)).isEqualTo(customerResponse);
        verify(customerService, times(1)).findAllCustomers();
    }

    @Test
    void testExistsById() {
        when(customerService.existsById("1")).thenReturn(true);

        ResponseEntity<Boolean> response = customerController.existsById("1");

        assertThat(response.getBody()).isTrue();
        verify(customerService, times(1)).existsById("1");
    }

    @Test
    void testFindById() {
        CustomerResponse customerResponse = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        when(customerService.findById("1")).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.findById("1");

        assertThat(response.getBody()).isEqualTo(customerResponse);
        verify(customerService, times(1)).findById("1");
    }

    @Test
    void testDeleteCustomer() {
        ResponseEntity<Void> response = customerController.delete("1");

        assertThat(response.getStatusCodeValue()).isEqualTo(202);
        verify(customerService, times(1)).deleteCustomer("1");
    }
}
