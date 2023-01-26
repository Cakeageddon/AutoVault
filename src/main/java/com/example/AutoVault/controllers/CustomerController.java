package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.CustomerDto;
import com.example.AutoVault.dtos.CustomerInputDto;
import com.example.AutoVault.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) { this.customerService = customerService;}

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) {
        CustomerDto customerDto = customerService.getOneCustomer(id);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/customers")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerInputDto customer) {
        CustomerDto customerSavedLocal = customerService.createCustomer(customer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customerSavedLocal.getId()).toUri();
        return ResponseEntity.created(location).body("Created customer with id: " + customerSavedLocal.getId());
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody CustomerInputDto c) {
        return ResponseEntity.ok(customerService.updateCustomer(id, c));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer with id: " + id + " is deleted.");
    }
}
