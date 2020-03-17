package com.vehicle.assessment.service;


import com.vehicle.assessment.domain.Vehicle;
import com.vehicle.assessment.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    public Callable<Vehicle> getById(UUID vin) throws Exception {
        Callable<Vehicle> vehicleCallable = () -> {
            Vehicle byId = repository.getById(vin);
            return byId;
        };
        return vehicleCallable;
    }

    public Callable<List<Vehicle>> getAll(){
      Callable<List<Vehicle>> streamCallable = () -> repository.findAll().stream().map(aLong -> repository.getById(aLong)).collect(Collectors.toList());
      return streamCallable;
    }

    public Vehicle save(Vehicle vehicle) throws Exception {
        return repository.save(vehicle);
    }
}
