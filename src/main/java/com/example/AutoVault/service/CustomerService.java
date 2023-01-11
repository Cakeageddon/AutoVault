package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CustomerDto;
import com.example.AutoVault.dtos.CustomerInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    public List<CustomerDto> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        List<CustomerDto> allCustomerDto = new ArrayList<>();
        for (Customer c : allCustomers) {
            allCustomerDto.add(transferToCustomerDto(c));
        }
        return allCustomerDto;
    }

    public CustomerDto getOneCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("No customer found with id: " + id);
        } else {
            Customer customer = optionalCustomer.get();
            return transferToCustomerDto(customer);
        }
    }

    public CustomerDto createCustomer(CustomerInputDto customer) {
        Customer customerSavedLocal = customerRepository.save(transferToCustomer(customer));

        return transferToCustomerDto(customerSavedLocal);
    }

    public CustomerDto updateCustomer(Long id, CustomerInputDto c) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("No customer found with id: " + id);
        } else {
            Customer updateCustomer = optionalCustomer.get();
            if (c.getId() != null) {updateCustomer.setId(c.getId());}
            if (c.getName() != null) {updateCustomer.setName(c.getName());}
            if (c.getAddress() != null) {updateCustomer.setAdress(c.getAddress());}
            if (c.getDateOfBirth() != null) {updateCustomer.setDateOfBirth(c.getDateOfBirth());}
            if (c.getGender() != null) {updateCustomer.setGender(c.getGender());}
            customerRepository.save(updateCustomer);
            return transferToCustomerDto(updateCustomer);
        }
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("No customer found with id: " + id);
        } else {
            customerRepository.deleteById(id);
        }
    }

    public static CustomerDto transferToCustomerDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setAddress(customer.getAdress());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setGender(customer.getGender());
        return dto;
    }

    public static Customer transferToCustomer (CustomerInputDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setAdress(dto.getAddress());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setGender(dto.getGender());
        return customer;
    }
}
