package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.StorageDto;
import com.example.AutoVault.dtos.StorageInputDto;
import com.example.AutoVault.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class StorageController {

    StorageService storageService;

    public StorageController(StorageService storageService) {this.storageService = storageService;}

    @GetMapping("/storage")
    public ResponseEntity<List<StorageDto>> getAllStorage() {
        return ResponseEntity.ok(storageService.getAllStorages());
    }

    @GetMapping("/storage/{id}")
    public ResponseEntity<StorageDto> getOneStorage(@PathVariable Long id) {
        StorageDto storageDto = storageService.getOneStorage(id);
        return ResponseEntity.ok(storageDto);
    }

    @PostMapping("/storage")
    public ResponseEntity<Object> createStorage(@RequestBody StorageInputDto storageInputDto) {
        StorageDto storageSavedLocal = storageService.createStorage(storageInputDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(storageSavedLocal.getId()).toUri();
        return ResponseEntity.created(location).body("Created storage with id: " + storageSavedLocal.getId());
    }

    @PutMapping("/storage/{id}")
    public ResponseEntity<Object> updateStorage(@PathVariable Long id, @RequestBody StorageInputDto s) {
        return ResponseEntity.ok(storageService.updateStorage(id, s));
    }

    @DeleteMapping("/storage/{id}")
    public ResponseEntity<Object> deleteStorage(@PathVariable Long id) {
        storageService.deleteStorage(id);
        return ResponseEntity.ok("Storage with id " + id + " is deleted.");
    }
}
