package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.CustomerDto;
import com.example.AutoVault.dtos.CustomerInputDto;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.security.JwtService;
import com.example.AutoVault.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtService jwtService;

    Customer customer1;
    Customer customer2;
    CustomerInputDto customerInputDto1;
    CustomerDto customer1Dto;
    CustomerDto customer2Dto;

    @BeforeEach
    public void setUp() {
        customer1 = new Customer(1L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(2L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        customerInputDto1 = new CustomerInputDto(3L, "Beppie", "Kampwagen 5", "14-08-1966", "Vrouw");
        customer1Dto = new CustomerDto(1L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2Dto = new CustomerDto(2L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
    }

    @Test
    void getAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(List.of(customer1Dto, customer2Dto));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(customer1Dto.getId().toString()))
                .andExpect(jsonPath("$[0].name").value("Henk"))
                .andExpect(jsonPath("$[0].address").value("Toverlaan 100"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("$[0].gender").value("Man"))
                .andExpect(jsonPath("$[1].id").value(customer2Dto.getId().toString()))
                .andExpect(jsonPath("$[1].name").value("Johannes"))
                .andExpect(jsonPath("$[1].address").value("Mariusstraat 12"))
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
                .andExpect(jsonPath("address").value("Toverlaan 100"))
                .andExpect(jsonPath("dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("gender").value("Man"));
    }
}
