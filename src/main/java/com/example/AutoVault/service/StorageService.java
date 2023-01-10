package com.example.AutoVault.service;

import com.example.AutoVault.dtos.StorageDto;
import com.example.AutoVault.dtos.StorageInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;
import com.example.AutoVault.models.Storage;
import com.example.AutoVault.repositories.StorageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository){this.storageRepository = storageRepository;}

    public List<StorageDto> getAllStorages() {
        List<Storage> allStorages = storageRepository.findAll();
        List<StorageDto> allStorageDto = new ArrayList<>();
        for (Storage s : allStorages) {
            allStorageDto.add(transferToStorageDto(s));
        }
        return allStorageDto;
    }

    public StorageDto getOneStorage(Long id) {
        Optional<Storage> optionalStorage = storageRepository.findById(id);
        if (optionalStorage.isEmpty()) {
            throw new RecordNotFoundException("No storage found with id: " + id);
        } else {
            Storage storage = optionalStorage.get();
            return transferToStorageDto(storage);
        }
    }

    public StorageDto createStorage(StorageInputDto storage) {
        Storage storageSavedLocal = storageRepository.save(transferToStorage(storage));
        return transferToStorageDto(storageSavedLocal);
    }

    public StorageDto updateStorage(Long id, StorageInputDto s) {
        Optional<Storage> optionalStorage = storageRepository.findById(id);
        if (optionalStorage.isEmpty()) {
            throw new RecordNotFoundException("No storage found with id: " + id);
        } else {
            Storage updateStorage = optionalStorage.get();
            if (s.getId() != null) {updateStorage.setId(s.getId());}
            if (s.getName() != null) {updateStorage.setName(s.getName());}
            if (s.getType() != null) {updateStorage.setType(s.getType());}
            if (s.getPrice() != null) {updateStorage.setPrice(s.getPrice());}
            storageRepository.save(updateStorage);
            return transferToStorageDto(updateStorage);
        }
    }

    public void deleteStorage(Long id) {
        Optional<Storage> optionalStorage = storageRepository.findById(id);
        if (optionalStorage.isEmpty()) {
            throw new RecordNotFoundException("No storage found with id: " + id);
        } else {
            storageRepository.deleteById(id);
        }
    }

    private StorageDto transferToStorageDto(Storage storage) {
        StorageDto dto = new StorageDto();
        dto.setId(storage.getId());
        dto.setName(storage.getName());
        dto.setType(storage.getType());
        dto.setPrice(storage.getPrice());
        return dto;
    }

    private Storage transferToStorage(StorageInputDto dto) {
        Storage storage = new Storage();
        storage.setName(dto.getName());
        storage.setType(dto.getType());
        storage.setPrice(dto.getPrice());
        return storage;
    }
}
