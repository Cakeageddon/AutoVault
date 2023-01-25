package com.example.AutoVault.models;


import javax.persistence.*;
import java.util.*;

@Entity

@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    private String licensePlate;
    private String serialNumber;
    private String make;
    private String type;
    private Long horsepower;
    private String fuelType;
    private String oilType;

    public Car() {
    }

    public Car(Long id, String licensePlate, String serialNumber, String make, String type, Long horsepower,
               String fuelType, String oilType, Customer customer, Storage storage, Set<Subscription> subscriptions) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.serialNumber = serialNumber;
        this.make = make;
        this.type = type;
        this.horsepower = horsepower;
        this.fuelType = fuelType;
        this.oilType = oilType;
        this.customer = customer;
        this.storage = storage;
        this.subscriptions = subscriptions;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private Storage storage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(indexes = @Index(name = "csno.index", columnList = "car_id"), joinColumns =
    @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "subscripton_id"), name = "car_subscription")
    private Set<Subscription> subscriptions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(licensePlate, car.licensePlate) && Objects.equals(serialNumber, car.serialNumber) && Objects.equals(make, car.make) && Objects.equals(type, car.type) && Objects.equals(horsepower, car.horsepower) && Objects.equals(fuelType, car.fuelType) && Objects.equals(oilType, car.oilType) && Objects.equals(customer, car.customer) && Objects.equals(storage, car.storage) && Objects.equals(subscriptions, car.subscriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, serialNumber, make, type, horsepower, fuelType, oilType, customer, storage, subscriptions);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Long horsepower) {
        this.horsepower = horsepower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
