package com.omar.ecommerce.customer;

import com.omar.ecommerce.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerRequest request = new CustomerRequest(null, "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", request.address());

        when(customerMapper.toCustomer(request)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);

        String id = customerService.createCustomer(request);

        assertThat(id).isEqualTo("1");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        Customer customer = new Customer("1", "Old", "Name", "old.email@example.com", new Address("456 Other St", "2B", "54321"));

        when(customerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(customerMapper.toCustomer(request)).thenReturn(customer);

        customerService.updateCustomer(request);

        assertThat(customer.getFirstname()).isEqualTo("John");
        assertThat(customer.getLastname()).isEqualTo("Doe");
        assertThat(customer.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(customer.getAddress()).isEqualTo(request.address());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testFindAllCustomers() {
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", customer.getAddress());

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));
        when(customerMapper.fromCustomer(customer)).thenReturn(response);

        List<CustomerResponse> customers = customerService.findAllCustomers();

        assertThat(customers).hasSize(1);
        assertThat(customers.get(0)).isEqualTo(response);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testExistsById() {
        when(customerRepository.findById("1")).thenReturn(Optional.of(new Customer()));

        Boolean exists = customerService.existsById("1");

        assertThat(exists).isTrue();
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    void testFindById_CustomerNotFound() {
        when(customerRepository.findById("1")).thenReturn(Optional.empty());

        try {
            customerService.findById("1");
        } catch (CustomerNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("No customer found with provided Id::1");
        }

        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteCustomer() {
        customerService.deleteCustomer("1");
        verify(customerRepository, times(1)).deleteById("1");
    }
}
