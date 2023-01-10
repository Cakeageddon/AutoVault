package com.example.AutoVault.controllers;

import com.example.AutoVault.dtos.CarDto;
import com.example.AutoVault.dtos.CarInputDto;
import com.example.AutoVault.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class CarController {
    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
        CarDto carDto = carService.getOneCar(id);
        return ResponseEntity.ok(carDto);
    }

    @PostMapping("/cars")
    public ResponseEntity<Object> createCar(@RequestBody CarInputDto car) {
        CarDto carSavedLocal = carService.createCar(car);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(carSavedLocal.getId()).toUri();
        return ResponseEntity.created(location).body("Created car with id: " + carSavedLocal.getId());
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable Long id, @RequestBody CarInputDto c) {
        return ResponseEntity.ok(carService.updateCar(id, c));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("Object with id: " + id + " is deleted");
    }

    @PutMapping("/cars/{carId}/subscription/{subscriptionId}")
    public ResponseEntity<Object> assignSubscriptionToCar(@PathVariable Long carId, @PathVariable Long subscriptionId) {
        carService.assignSubscriptionToCar(carId, subscriptionId);
        return ResponseEntity.ok().body("Added subscription with id: " + subscriptionId + " to car with id: " + carId);
    }

    @PutMapping("/cars/{carId}/storage/{storageId}")
    public ResponseEntity<Object> assignStorageToCar(@PathVariable Long carId, @PathVariable Long storageId) {
        carService.assignStorageToCar(carId, storageId);
        return ResponseEntity.ok().body("Added storage with id: " + storageId + " to car with id: " + carId);
    }
}
