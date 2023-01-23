package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CarInputDto;
import com.example.AutoVault.models.Car;
import com.example.AutoVault.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository repos;

    @InjectMocks
    CarService service;

    Car car1;
    Car car2;

    @BeforeEach
    void setUp() {
        car1 = new Car(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50");
        car2 = new Car(2L, "CC-33-DD", "987654ABC", "Mercedes", "Benz1", 12L, "Benzeen", "Walvisolie");


    }

    @Test
    void createCar() {
        CarInputDto cdto = new CarInputDto(9900L, "BB-33-66", "1354987ASD", "Mitsubishi", "Zamboni", 20L, "Diesel", "0w-20");


    }
}