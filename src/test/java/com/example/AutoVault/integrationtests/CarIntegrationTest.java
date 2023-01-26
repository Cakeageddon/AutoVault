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
    CarDto carDto1;
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
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        storage1 = new Storage(100L, "Basic", "Onverwarmd", 150);
        storage2 = new Storage(200L, "Premium", "Climate control & Luchtcirculatie", 400);
        storage3 = new Storage(300L, "Basic+", "Climate control", 225);
        storageRepository.save(storage1);
        storageRepository.save(storage2);
        storageRepository.save(storage3);

        subscription1 = new Subscription(1000L, 10.0, "Druppellader");
        subscription2 = new Subscription(2000L, 20.0, "Ventilator");
        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);

        car1 = new Car(1L, "AA-12-BB", "123AB", "Ford", "Mk1", 2L, "Stoom", "50w50", customer1, storage1, null);
        car2 = new Car(2L, "CC-33-DD", "987654ABC", "Mercedes", "Benz1", 12L, "Benzeen", "Walvisolie", customer2, storage2, (Set<Subscription>) subscription2);
        car3 = new Car(3L, "EE-44-FF", "654fgh321", "Fiat", "500", 22L, "Benzine", "10w30", null,null, null);
        car4 = new Car(4L, "GG-55-HH", "21365465", "Toyota", "Trueno", 180L, "Benzine", "10w30", customer1,null, null);
        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);

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
                .andExpect(jsonPath("customerDto.id").value(customer1.getId().toString()));

//                .andExpect(jsonPath("storage").value("$[]"))
//                .andExpect(jsonPath("subscriptions").value("$[]"));

    }

    @Test
    void getAllCars() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())

    }
}
