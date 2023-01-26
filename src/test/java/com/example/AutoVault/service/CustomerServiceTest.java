package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CustomerDto;
import com.example.AutoVault.dtos.CustomerInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Captor
    ArgumentCaptor<Customer> argumentCaptor;

    Customer customer1;
    Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(10L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(20L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(List.of(customer1,customer2));

        List<CustomerDto> customersFound = customerService.getAllCustomers();

        assertEquals(customer1.getName(), customersFound.get(0).getName());
        assertEquals(customer2.getAdress(), customersFound.get(1).getAddress());
    }

    @Test
    void getOneCustomer() {
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer1));
        CustomerDto customerDto = CustomerService.transferToCustomerDto(customer1);

        CustomerDto cdto = customerService.getOneCustomer(10L);

        assertEquals(customerDto.getName(), cdto.getName());
    }

    @Test
    void getOneCustomerExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> customerService.getOneCustomer(11L));
    }

    @Test
    void createCustomer() {
        CustomerInputDto cdto = new CustomerInputDto(10L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        customerService.createCustomer(cdto);

        verify(customerRepository, times(1)).save(argumentCaptor.capture());
        Customer customer = argumentCaptor.getValue();

        assertEquals(customer1.getName(), customer.getName());
        assertEquals(customer1.getAdress(), customer.getAdress());
        assertEquals(customer1.getDateOfBirth(), customer.getDateOfBirth());
        assertEquals(customer1.getGender(), customer.getGender());
    }

    @Test
    void updateCustomer() {
        CustomerInputDto customerInputDto = new CustomerInputDto(30L, "Annie", "Bosjesstraat 65", "26-06-1966", "Vrouw");
        Customer customer = new Customer(30L, "Annie", "Bosjesstraat 65", "26-06-1966", "Vrouw");
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any())).thenReturn(customer);

        customerService.updateCustomer(10L, customerInputDto);

        verify(customerRepository, times(1)).save(argumentCaptor.capture());
        Customer captured = argumentCaptor.getValue();

        assertEquals(customer.getName(), captured.getName());
        assertEquals(customer.getAdress(), captured.getAdress());
        assertEquals(customer.getDateOfBirth(), captured.getDateOfBirth());
        assertEquals(customer.getGender(), captured.getGender());
    }

    @Test
    void updateCustomerExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> customerService.updateCustomer(11L, new CustomerInputDto(30L, "Annie", "Bosjesstraat 65", "26-06-1966", "Vrouw")));
    }

    @Test
    void deleteCustomer() {
        when(customerRepository.findById(20L)).thenReturn(Optional.of(customer2));
        customerService.deleteCustomer(20L);
        verify(customerRepository).deleteById(20L);
    }

    @Test
    void deleteCustomerExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> customerService.deleteCustomer(44L));
    }
}