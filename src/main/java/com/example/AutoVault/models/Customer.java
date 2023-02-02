package com.example.AutoVault.models;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String dateOfBirth;
    private String gender;

    public Customer() {
    }

    public Customer(Long id, String name, String adress, String dateOfBirth, String gender){
        this.id = id;
        this.address = adress;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.name = name;
    }

    @OneToMany(mappedBy = "customer")
    private List<Car> car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name) && address.equals(customer.address) && dateOfBirth.equals(customer.dateOfBirth) && gender.equals(customer.gender) && car.equals(customer.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, dateOfBirth, gender, car);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

}