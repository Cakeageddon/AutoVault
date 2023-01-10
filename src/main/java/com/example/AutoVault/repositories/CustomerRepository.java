package com.example.AutoVault.repositories;

import com.example.AutoVault.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
