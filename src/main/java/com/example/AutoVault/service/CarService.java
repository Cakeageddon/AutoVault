package com.example.AutoVault.service;

import com.example.AutoVault.dtos.CarDto;
import com.example.AutoVault.dtos.CarInputDto;
import com.example.AutoVault.exceptions.RecordNotFoundException;
import com.example.AutoVault.models.Car;
import com.example.AutoVault.models.Storage;
import com.example.AutoVault.models.Subscription;
import com.example.AutoVault.repositories.CarRepository;
import com.example.AutoVault.repositories.StorageRepository;
import com.example.AutoVault.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository carRepository;
    SubscriptionRepository subscriptionRepository;
    StorageRepository storageRepository;


    public CarService(CarRepository carRepository, StorageRepository storageRepository, SubscriptionRepository subscriptionRepository) {
        this.carRepository = carRepository;
        this.storageRepository = storageRepository;
        this. subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void assignSubscriptionToCar(Long carId, Long subscriptionId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        if (optionalCar.isEmpty() && optionalSubscription.isEmpty()) {
            throw new RecordNotFoundException("No car/subscription combination found. Try different id(s).");
        } else {
            Car car = optionalCar.get();
            Subscription subscription = optionalSubscription.get();

            List<Subscription> subscriptions = car.getSubscriptions();
            subscriptions.add(subscription);
            car.setSubscriptions(subscriptions);
            carRepository.save(car);

            subscription.setCar(car);
            subscriptionRepository.save(subscription);
        }
    }

    public void assignStorageToCar(Long carId, Long storageId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Storage> optionalStorage = storageRepository.findById(storageId);

        if (optionalCar.isEmpty() && optionalStorage.isEmpty()) {
            throw new RecordNotFoundException("No Car/storage subscription combination found. Try different id(s).");
        } else {
            Car car = optionalCar.get();
            Storage storage = optionalStorage.get();
            car.setStorage(storage);
            carRepository.save(car);
        }
    }

    public List<CarDto> getAllCars() {
        List<Car> allCars = carRepository.findAll();
        List<CarDto> allCarDto = new ArrayList<>();
        for (Car c : allCars) {
            allCarDto.add(transferToCarDto(c));
        }
        return allCarDto;
    }

    public CarDto getOneCar(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);

        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car found with id: " + id);
        } else {
            Car car = optionalCar.get();
            return transferToCarDto(car);
        }
    }

    public CarDto createCar(CarInputDto car) {
        Car carSavedLocal = carRepository.save(transferToCar(car));

        return transferToCarDto(carSavedLocal);
    }

    public CarDto updateCar(Long id, CarInputDto c) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car found with id: " + id);
        } else {
            Car updateCar = optionalCar.get();
            if (c.getId() != null) {updateCar.setId(c.getId());}
            if (c.getLicensePlate() != null) {updateCar.setLicensePlate(c.getLicensePlate());}
            if (c.getSerialNumber() != null) {updateCar.setSerialNumber(c.getSerialNumber());}
            if (c.getMake() != null) {updateCar.setMake(c.getMake());}
            if (c.getType() != null) {updateCar.setType(c.getType());}
            if (c.getHorsepower() != null) {updateCar.setHorsepower(c.getHorsepower());}
            if (c.getFuelType() != null) {updateCar.setFuelType(c.getFuelType());}
            if (c.getOilType() != null) {updateCar.setOilType(c.getOilType());}
            carRepository.save(updateCar);
            return transferToCarDto(updateCar);
        }
    }

    public void deleteCar(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()) {
            throw new RecordNotFoundException("No car found with id: " + id);
        } else {
            carRepository.deleteById(id);
        }
    }



    private CarDto transferToCarDto(Car car) {
        CarDto dto = new CarDto();
        dto.setId(car.getId());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setSerialNumber(car.getSerialNumber());
        dto.setMake(car.getMake());
        dto.setType(car.getType());
        dto.setHorsepower(car.getHorsepower());
        dto.setFuelType(car.getFuelType());
        dto.setOilType(car.getOilType());
        return dto;
    }

    private Car transferToCar(CarInputDto dto) {
        Car car = new Car();
        car.setLicensePlate(dto.getLicensePlate());
        car.setSerialNumber(dto.getSerialNumber());
        car.setMake(dto.getMake());
        car.setType(dto.getType());
        car.setHorsepower(dto.getHorsepower());
        car.setFuelType(dto.getFuelType());
        car.setOilType(dto.getOilType());
        return car;
    }
}