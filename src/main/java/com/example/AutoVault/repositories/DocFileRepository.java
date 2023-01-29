package com.example.AutoVault.repositories;

import com.example.AutoVault.models.DocFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocFileRepository extends JpaRepository<DocFile, Long> {
    DocFile findByFileName(String fileName);
}
