package com.example.AutoVault.models;

import javax.persistence.*;

import java.util.List;

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
    }

    @OneToMany(mappedBy = "customer")
    private List<Car> car;

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