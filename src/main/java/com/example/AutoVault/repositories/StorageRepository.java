package com.example.AutoVault.repositories;

import com.example.AutoVault.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
