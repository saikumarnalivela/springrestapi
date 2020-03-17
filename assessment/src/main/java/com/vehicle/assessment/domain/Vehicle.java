package com.vehicle.assessment.domain;

import java.util.Objects;
import java.util.UUID;

public class Vehicle {


    private UUID vin;
    private int year;
    private String make;
    private String model;
    private TransmissionType transmissionType;

    public Vehicle(int year, String make, String model, TransmissionType transmissionType) {
        this.vin = UUID.randomUUID();
        this.year = year;
        this.make = make;
        this.model = model;
        this.transmissionType = transmissionType;
    }

    public Vehicle() {
        this.vin = UUID.randomUUID();
    }

    public UUID getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TransmissionType getTransmissionType() {

        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;

        return vin.equals(vehicle.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                ", vin=" + vin +
                ", year=" + year +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", transmissionType=" + transmissionType +
                '}';
    }
}
