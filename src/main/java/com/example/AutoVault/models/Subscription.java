package com.example.AutoVault.models;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    private Double price;
    private String type;

    public Subscription() {
    }

    public Subscription(Long id, Double price, String type) {
        this.id = id;
        this.price = price;
        this.type = type;
    }

    @ManyToMany(mappedBy = "subscriptions")
    private Set<Car> car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Car> getCar() {
        return car;
    }

    public void setCar(Set<Car> car) {
        this.car = car;
    }
}
