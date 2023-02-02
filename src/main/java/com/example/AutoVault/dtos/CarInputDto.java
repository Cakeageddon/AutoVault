package com.example.AutoVault.dtos;

public class CarInputDto {
    private Long id;
    private String licensePlate;
    private String serialNumber;
    private String make;
    private String type;
    private Long horsepower;
    private String fuelType;
    private String oilType;

    public CarInputDto() {
    }

    public CarInputDto(String licensePlate, String serialNumber, String make, String type, Long horsepower, String fuelType, String oilType) {
        this.licensePlate = licensePlate;
        this.serialNumber = serialNumber;
        this.make = make;
        this.type = type;
        this.horsepower = horsepower;
        this.fuelType = fuelType;
        this.oilType = oilType;
    }

    public CarInputDto(Long id, String licensePlate, String serialNumber, String make, String type, Long horsepower, String fuelType, String oilType) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.serialNumber = serialNumber;
        this.make = make;
        this.type = type;
        this.horsepower = horsepower;
        this.fuelType = fuelType;
        this.oilType = oilType;
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
}
