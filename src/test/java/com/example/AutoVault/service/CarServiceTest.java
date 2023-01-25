package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CarDto;
import com.example.AutoVault.dtos.CarInputDto;
import com.example.AutoVault.models.Car;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.models.Storage;
import com.example.AutoVault.models.Subscription;
import com.example.AutoVault.repositories.CarRepository;
import com.example.AutoVault.repositories.CustomerRepository;
import com.example.AutoVault.repositories.StorageRepository;
import com.example.AutoVault.repositories.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;
    @Mock
    SubscriptionRepository subscriptionRepository;
    @Mock
    StorageRepository storageRepository;
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CarService carService;

    @Captor
    ArgumentCaptor<Car> captor;

    Car car1;
    Car car2;
    Customer customer1;
    Customer customer2;
    Storage storage1;
    Storage storage2;
    Subscription subscription1;
    Subscription subscription2;

    @BeforeEach
    void setUp() {
        car1 = new Car(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50", customer1, storage1, (Set<Subscription>) subscription1);
        car2 = new Car(2L, "CC-33-DD", "987654ABC", "Mercedes", "Benz1", 12L, "Benzeen", "Walvisolie", customer2, storage2, (Set<Subscription>) subscription2);
        customer1 = new Customer(10L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(20L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        storage1 = new Storage(100L, "Basic", "Onverwarmd", 150);
        storage2 = new Storage(200L, "Premium", "Climate control & Luchtcirculatie", 400);
        subscription1 = new Subscription(1000L, 10.0, "Druppellader");
        subscription2 = new Subscription(2000L, 20.0, "Ventilator");
    }

    @Test
    void getAllCars() throws Exception {
        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        List<CarDto> carsFound = carService.getAllCars();

        assertEquals(car1.getLicensePlate(), carsFound.get(0).getLicensePlate());
        assertEquals(car2.getLicensePlate(), carsFound.get(1).getLicensePlate());
    }

    @Test
    void getOneCar() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));
        CarDto car1Dto = carService.transferToCarDto(car1);

        CarDto dto = carService.getOneCar(1L);

        assertEquals(car1Dto.getSubscriptionDto(), dto.getSubscriptionDto());
    }

    @Test
    void createCar() throws Exception {
        CarInputDto cdto = new CarInputDto(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50");

        carService.createCar(cdto);
        verify(carRepository, times(1)).save(captor.capture());
        Car car = captor.getValue();

        assertEquals(car1.getLicensePlate(), car.getLicensePlate());
        assertEquals(car1.getSerialNumber(), car.getSerialNumber());
        assertEquals(car1.getMake(), car.getMake());
        assertEquals(car1.getType(), car.getType());
        assertEquals(car1.getHorsepower(), car.getHorsepower());
        assertEquals(car1.getFuelType(), car.getFuelType());
        assertEquals(car1.getOilType(), car.getOilType());
    }
}