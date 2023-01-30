package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CarDto;
import com.example.AutoVault.dtos.CarInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;
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

import java.util.HashSet;
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
    Car car3;
    Car car4;
    Customer customer1;
    Customer customer2;
    Storage storage1;
    Storage storage2;
    Storage storage3;
    Subscription subscription1;
    Subscription subscription2;
    HashSet<Subscription> subscriptions3;

    @BeforeEach
    void setUp() {

        customer1 = new Customer(10L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(20L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        storage1 = new Storage(100L, "Basic", "Onverwarmd", 150);
        storage2 = new Storage(200L, "Premium", "Climate control & Luchtcirculatie", 400);
        storage3 = new Storage(300L, "Basic+", "Climate control", 225);
        subscription1 = new Subscription(1000L, 10.0, "Druppellader");
        subscription2 = new Subscription(2000L, 20.0, "Ventilator");
        car1 = new Car(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50", customer1, storage1, Set.of(subscription1,subscription2), null);
        car2 = new Car(2L, "CC-33-DD", "987654ABC", "Mercedes", "Benz1", 12L, "Benzeen", "Walvisolie", customer2, storage2, subscriptions3, null);
        car3 = new Car(3L, "EE-44-FF", "654fgh321", "Fiat", "500", 22L, "Benzine", "10w30");
        car4 = new Car(4L, "GG-55-HH", "21365465", "Toyota", "Trueno", 180L, "Benzine", "10w30", customer1,null, null, null);
    }

    @Test
    void assignSubscriptionToCar() {
        when(carRepository.findById(2L)).thenReturn(Optional.of(car2));
        when(subscriptionRepository.findById(2000L)).thenReturn(Optional.of(subscription2));
        when(subscriptionRepository.findById(1000L)).thenReturn(Optional.of(subscription1));
        carService.assignSubscriptionToCar(2L, 1000L);
        carService.assignSubscriptionToCar(2L, 2000L);

        verify(carRepository, times(2)).save(captor.capture());
        Car car = captor.getValue();
        System.out.println(car2.getSubscriptions());
        assertEquals(car.getSubscriptions(), car2.getSubscriptions());
    }


    @Test
    void assignSubscriptionToCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.assignSubscriptionToCar(1L, 1001L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignSubscriptionToCar(8L, 1000L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignSubscriptionToCar(8L, 1001L));
    }

    @Test
    void assignStorageToCar() {
        when(carRepository.findById(3L)).thenReturn(Optional.of(car3));
        when(storageRepository.findById(300L)).thenReturn(Optional.of(storage3));


        carService.assignStorageToCar(3L, 300L);
        verify(carRepository, times(1)).save(captor.capture());
        Car car = captor.getValue();

        assertEquals(car.getStorage(), storage3);
    }

    @Test
    void assignStorageToCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.assignStorageToCar(3L, 301L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignStorageToCar(4L, 300L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignStorageToCar(4L, 301L));
    }

    @Test
    void assignCustomerToCar() {
        when(carRepository.findById(3L)).thenReturn(Optional.of(car3));
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer1));

        carService.assignCustomerToCar(3L, 10L);
        verify(carRepository, times(1)).save(captor.capture());
        Car car = captor.getValue();

        assertEquals(car.getCustomer(), customer1);
    }

    @Test
    void assignCustomerToCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.assignCustomerToCar(7L,10L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignCustomerToCar(1L,11L));
        assertThrows(RecordNotFoundException.class, () -> carService.assignCustomerToCar(7L,11L));
    }

    @Test
    void getAllCars() {
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
    void getOneCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.getOneCar(4L));
    }

    @Test
    void createCar() {
        CarInputDto cdto = new CarInputDto(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50");
        when(carRepository.save(any(Car.class))).thenReturn(car1);
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

    @Test
    void updateCar() {
        CarInputDto carInputDto = new CarInputDto(5L, "6-AHF-32", "654as978q", "Ford", "Focus", 143L, "Diesel", "10w40");
        Car car3 = new Car(5L, "6-AHF-32", "654as978q", "Ford", "Focus", 143L, "Diesel", "10w40", null, null, null, null);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));
        when(carRepository.save(any())).thenReturn(car3);

        carService.updateCar(1L, carInputDto);

        verify(carRepository, times(1)).save(captor.capture());

        Car captured = captor.getValue();

        assertEquals(car3.getLicensePlate(), captured.getLicensePlate());
        assertEquals(car3.getSerialNumber(), captured.getSerialNumber());
        assertEquals(car3.getMake(), captured.getMake());
        assertEquals(car3.getType(), captured.getType());
        assertEquals(car3.getHorsepower(), captured.getHorsepower());
        assertEquals(car3.getFuelType(), captured.getFuelType());
        assertEquals(car3.getOilType(), captured.getOilType());
    }

    @Test
    void updateCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.updateCar(6L, new CarInputDto(5L, "6-AHF-32", "654as978q", "Ford", "Focus", 143L, "Diesel", "10w40")));
    }

    @Test
    void deleteCar() {
        when(carRepository.findById(2L)).thenReturn(Optional.of(car2));

        carService.deleteCar(2L);

        verify(carRepository).deleteById(2L);
    }

    @Test
    void deleteCarExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> carService.deleteCar(7L));
    }
}