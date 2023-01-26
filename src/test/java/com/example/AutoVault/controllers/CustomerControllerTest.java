package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.CustomerDto;
import com.example.AutoVault.dtos.CustomerInputDto;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.repositories.CustomerRepository;
import com.example.AutoVault.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private CustomerService customerService;

    @Autowired
    @Mock
    private CustomerRepository customerRepository;

    Customer customer1;
    Customer customer2;
    CustomerInputDto customerInputDto1;
    CustomerDto customer1Dto;
    CustomerDto customer2Dto;

    @BeforeEach
    public void setUp() {
        if (customerRepository.count() > 0) {
            customerRepository.deleteAll();
        }
        customer1 = new Customer(1L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(2L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        customerInputDto1 = new CustomerInputDto(3L, "Beppie", "Kampwagen 5", "14-08-1966", "Vrouw");
        customer1Dto = new CustomerDto(1L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2Dto = new CustomerDto(2L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customer1Dto.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Henk"))
                .andExpect(jsonPath("$[0].adress").value("Toverlaan 100"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("$[0].gender").value("Man"))
                .andExpect(jsonPath("$[1].id").value(customer2.getId().toString()))
                .andExpect(jsonPath("$[1].name").value("Johannes"))
                .andExpect(jsonPath("$[1].adress").value("Mariusstraat 12"))
                .andExpect(jsonPath("$[1].dateOfBirth").value("27-02-1975"))
                .andExpect(jsonPath("$[1].gender").value("Vrouw"));
    }

    @Test
    void getOneCustomer() throws Exception {
        when(customerService.getOneCustomer(1L)).thenReturn(customer1Dto);
        mockMvc.perform(get("/customers/" + customer1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer1Dto.getId().toString()))
                .andExpect(jsonPath("name").value("Henk"))
                .andExpect(jsonPath("adress").value("Toverlaan 100"))
                .andExpect(jsonPath("dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("gender").value("Man"));
    }
}
