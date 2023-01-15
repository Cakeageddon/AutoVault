package com.example.AutoVault.repositories;

import com.example.AutoVault.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
