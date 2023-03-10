package com.example.AutoVault.dtos;

import java.util.Set;

public class CarDto {
    private Long id;
    private String licensePlate;
    private String serialNumber;
    private String make;
    private String type;
    private Long horsepower;
    private String fuelType;
    private String oilType;

    private String docFileDtoFilename;
    private Set<SubscriptionDto> subscriptionDto;

    private StorageDto storageDto;

    private CustomerDto customerDto;




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

    public Set<SubscriptionDto> getSubscriptionDto() {
        return subscriptionDto;
    }

    public void setSubscriptionDto(Set<SubscriptionDto> subscriptionDto) {
        this.subscriptionDto = subscriptionDto;
    }

    public StorageDto getStorageDto() {
        return storageDto;
    }

    public void setStorageDto(StorageDto storageDto) {
        this.storageDto = storageDto;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public String getDocFileDtoFilename() {
        return docFileDtoFilename;
    }

    public void setDocFileDtoId(String docFileDtoFilename) {
        this.docFileDtoFilename = docFileDtoFilename;
    }
}
