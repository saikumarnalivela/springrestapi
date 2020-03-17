package com.vehicle.assessment.repository;


import com.vehicle.assessment.annotations.Loggable;
import com.vehicle.assessment.domain.TransmissionType;
import com.vehicle.assessment.domain.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class VehicleRepository {

    private static final List<Vehicle> vehicleList = new ArrayList<>();

    static {
        vehicleList.add(new Vehicle(2019,"FCA","RAM", TransmissionType.MANUAL));
        vehicleList.add(new Vehicle(2018,"TESLA","RAM", TransmissionType.AUTO));
        vehicleList.add(new Vehicle(2020,"FORD","MAR", TransmissionType.AUTO));
        vehicleList.add(new Vehicle(2016,"HUNDAI","ROM", TransmissionType.MANUAL));
    }



    @Loggable
    public Vehicle getById(UUID vin) throws IllegalArgumentException {

        Vehicle vehicles = null;
        try {
            vehicles  = vehicleList.stream().filter(vehicle -> vehicle.getVin().equals(vin))
                    .findFirst().get();
        } catch (IllegalArgumentException ie) {
            throw new IllegalArgumentException(ie.getMessage());
        }
        return vehicles;
    }

    @Loggable
    public List<UUID> findAll() {
        return vehicleList.stream().map(Vehicle::getVin).collect(Collectors.toList());
    }

    @Loggable
    public Vehicle save(Vehicle vehicle) throws Exception {
        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setMake(vehicle.getMake());
        savedVehicle.setModel(vehicle.getModel());
        savedVehicle.setYear(vehicle.getYear());
        savedVehicle.setTransmissionType(vehicle.getTransmissionType());
        boolean add = vehicleList.add(savedVehicle);
        if(add){
            return savedVehicle;
        }
        else{
             throw new Exception("Not Saved");
        }

    }


}
