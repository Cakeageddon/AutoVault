package com.example.AutoVault.integrationtests;

import com.example.AutoVault.dtos.CarDto;
import com.example.AutoVault.models.Car;
import com.example.AutoVault.models.Customer;
import com.example.AutoVault.models.Storage;
import com.example.AutoVault.models.Subscription;
import com.example.AutoVault.repositories.CarRepository;
import com.example.AutoVault.repositories.CustomerRepository;
import com.example.AutoVault.repositories.StorageRepository;
import com.example.AutoVault.repositories.SubscriptionRepository;
import com.example.AutoVault.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CarIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    StorageRepository storageRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

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



    @BeforeEach
    void setUp() {
        if (carRepository.count() > 0){
            carRepository.deleteAll();
        }
        customer1 = new Customer(10L, "Henk", "Toverlaan 100", "19-01-1993", "Man");
        customer2 = new Customer(20L, "Johannes", "Mariusstraat 12", "27-02-1975", "Vrouw");
        customer1 = customerRepository.save(customer1);
        customer2 = customerRepository.save(customer2);

        storage1 = new Storage(100L, "Basic", "Onverwarmd", 150);
        storage2 = new Storage(200L, "Premium", "Climate control & Luchtcirculatie", 400);
        storage3 = new Storage(300L, "Basic+", "Climate control", 225);
        storage1 = storageRepository.save(storage1);
        storage2 = storageRepository.save(storage2);
        storage3 = storageRepository.save(storage3);

        subscription1 = new Subscription(1000L, 10.0, "Druppellader");
        subscription2 = new Subscription(2000L, 20.0, "Ventilator");
        subscription1 = subscriptionRepository.save(subscription1);
        subscription2 = subscriptionRepository.save(subscription2);

        car1 = new Car(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50", customer1, storage1, Set.of(subscription1,subscription2));
        car2 = new Car(2L, "CC-33-DD", "987654ABC", "Mercedes", "Benz1", 12L, "Benzeen", "Walvisolie", customer2, storage2, Set.of(subscription2));
        car3 = new Car(3L, "EE-44-FF", "654fgh321", "Fiat", "500", 22L, "Benzine", "10w30");
        car4 = new Car(4L, "GG-55-HH", "21365465", "Toyota", "Trueno", 180L, "Benzine", "10w30", customer1,storage3, Set.of(subscription1));
        car1 = carRepository.save(car1);
        car2 = carRepository.save(car2);
        car3 = carRepository.save(car3);
        car4 = carRepository.save(car4);
    }

    @Test
    void getOneCar() throws Exception {

        mockMvc.perform(get("/cars/" + car1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(car1.getId().toString()))
                .andExpect(jsonPath("licensePlate").value("AA-12-BB"))
                .andExpect(jsonPath("serialNumber").value("123AB"))
                .andExpect(jsonPath("make").value("Ford"))
                .andExpect(jsonPath("type").value("Mk1"))
                .andExpect(jsonPath("horsepower").value(2))
                .andExpect(jsonPath("fuelType").value("Stoom"))
                .andExpect(jsonPath("oilType").value("50w50"))
                .andExpect(jsonPath("customerDto.id").value(customer1.getId().toString()))
                .andExpect(jsonPath("customerDto.name").value("Henk"))
                .andExpect(jsonPath("customerDto.address").value("Toverlaan 100"))
                .andExpect(jsonPath("customerDto.dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("customerDto.gender").value("Man"))
                .andExpect(jsonPath("storageDto.id").value(storage1.getId().toString()))
                .andExpect(jsonPath("storageDto.name").value("Basic"))
                .andExpect(jsonPath("storageDto.type").value("Onverwarmd"))
                .andExpect(jsonPath("storageDto.price").value(150))
                .andExpect(jsonPath("subscriptionDto.[0].id").value(subscription2.getId().toString()))
                .andExpect(jsonPath("subscriptionDto.[0].price").value(20.0))
                .andExpect(jsonPath("subscriptionDto.[0].type").value("Ventilator"))
                .andExpect(jsonPath("subscriptionDto.[1].id").value(subscription1.getId().toString()))
                .andExpect(jsonPath("subscriptionDto.[1].price").value(10.0))
                .andExpect(jsonPath("subscriptionDto.[1].type").value("Druppellader"));
    }

    @Test
    void getAllCars() throws Exception {

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(car1.getId().toString()))
                .andExpect(jsonPath("$[0].licensePlate").value("AA-12-BB"))
                .andExpect(jsonPath("$[0].serialNumber").value("123AB"))
                .andExpect(jsonPath("$[0].make").value("Ford"))
                .andExpect(jsonPath("$[0].type").value("Mk1"))
                .andExpect(jsonPath("$[0].horsepower").value(2))
                .andExpect(jsonPath("$[0].fuelType").value("Stoom"))
                .andExpect(jsonPath("$[0].oilType").value("50w50"))
                .andExpect(jsonPath("$[0].customerDto.id").value(customer1.getId().toString()))
                .andExpect(jsonPath("$[0].customerDto.name").value("Henk"))
                .andExpect(jsonPath("$[0].customerDto.address").value("Toverlaan 100"))
                .andExpect(jsonPath("$[0].customerDto.dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("$[0].customerDto.gender").value("Man"))
                .andExpect(jsonPath("$[0].storageDto.id").value(storage1.getId().toString()))
                .andExpect(jsonPath("$[0].storageDto.name").value("Basic"))
                .andExpect(jsonPath("$[0].storageDto.type").value("Onverwarmd"))
                .andExpect(jsonPath("$[0].storageDto.price").value(150))
                .andExpect(jsonPath("$[0].subscriptionDto.[0].id").value(subscription1.getId().toString()))
                .andExpect(jsonPath("$[0].subscriptionDto.[0].price").value(10.0))
                .andExpect(jsonPath("$[0].subscriptionDto.[0].type").value("Druppellader"))
                .andExpect(jsonPath("$[0].subscriptionDto.[1].id").value(subscription2.getId().toString()))
                .andExpect(jsonPath("$[0].subscriptionDto.[1].price").value(20.0))
                .andExpect(jsonPath("$[0].subscriptionDto.[1].type").value("Ventilator"))

                .andExpect(jsonPath("$[1].id").value(car2.getId().toString()))
                .andExpect(jsonPath("$[1].licensePlate").value("CC-33-DD"))
                .andExpect(jsonPath("$[1].serialNumber").value("987654ABC"))
                .andExpect(jsonPath("$[1].make").value("Mercedes"))
                .andExpect(jsonPath("$[1].type").value("Benz1"))
                .andExpect(jsonPath("$[1].horsepower").value(12))
                .andExpect(jsonPath("$[1].fuelType").value("Benzeen"))
                .andExpect(jsonPath("$[1].oilType").value("Walvisolie"))
                .andExpect(jsonPath("$[1].customerDto.id").value(customer2.getId().toString()))
                .andExpect(jsonPath("$[1].customerDto.name").value("Johannes"))
                .andExpect(jsonPath("$[1].customerDto.address").value("Mariusstraat 12"))
                .andExpect(jsonPath("$[1].customerDto.dateOfBirth").value("27-02-1975"))
                .andExpect(jsonPath("$[1].customerDto.gender").value("Vrouw"))
                .andExpect(jsonPath("$[1].storageDto.id").value(storage2.getId().toString()))
                .andExpect(jsonPath("$[1].storageDto.name").value("Premium"))
                .andExpect(jsonPath("$[1].storageDto.type").value("Climate control & Luchtcirculatie"))
                .andExpect(jsonPath("$[1].storageDto.price").value(400))
                .andExpect(jsonPath("$[1].subscriptionDto.[0].id").value(subscription2.getId().toString()))
                .andExpect(jsonPath("$[1].subscriptionDto.[0].price").value(20.0))
                .andExpect(jsonPath("$[1].subscriptionDto.[0].type").value("Ventilator"))

                .andExpect(jsonPath("$[2].id").value(car3.getId().toString()))
                .andExpect(jsonPath("$[2].licensePlate").value("EE-44-FF"))
                .andExpect(jsonPath("$[2].serialNumber").value("654fgh321"))
                .andExpect(jsonPath("$[2].make").value("Fiat"))
                .andExpect(jsonPath("$[2].type").value("500"))
                .andExpect(jsonPath("$[2].horsepower").value(22))
                .andExpect(jsonPath("$[2].fuelType").value("Benzine"))
                .andExpect(jsonPath("$[2].oilType").value("10w30"))

                .andExpect(jsonPath("$[3].id").value(car4.getId().toString()))
                .andExpect(jsonPath("$[3].licensePlate").value("GG-55-HH"))
                .andExpect(jsonPath("$[3].serialNumber").value("21365465"))
                .andExpect(jsonPath("$[3].make").value("Toyota"))
                .andExpect(jsonPath("$[3].type").value("Trueno"))
                .andExpect(jsonPath("$[3].horsepower").value(180))
                .andExpect(jsonPath("$[3].fuelType").value("Benzine"))
                .andExpect(jsonPath("$[3].oilType").value("10w30"))
                .andExpect(jsonPath("$[3].customerDto.id").value(customer1.getId().toString()))
                .andExpect(jsonPath("$[3].customerDto.name").value("Henk"))
                .andExpect(jsonPath("$[3].customerDto.address").value("Toverlaan 100"))
                .andExpect(jsonPath("$[3].customerDto.dateOfBirth").value("19-01-1993"))
                .andExpect(jsonPath("$[3].customerDto.gender").value("Man"))
                .andExpect(jsonPath("$[3].storageDto.id").value(storage3.getId().toString()))
                .andExpect(jsonPath("$[3].storageDto.name").value("Basic+"))
                .andExpect(jsonPath("$[3].storageDto.type").value("Climate control"))
                .andExpect(jsonPath("$[3].storageDto.price").value(225))
                .andExpect(jsonPath("$[3].subscriptionDto.[0].id").value(subscription1.getId().toString()))
                .andExpect(jsonPath("$[3].subscriptionDto.[0].price").value(10.0))
                .andExpect(jsonPath("$[3].subscriptionDto.[0].type").value("Druppellader"));
    }
}
