package com.vehicle.assessment.controllers;


import com.vehicle.assessment.annotations.Loggable;
import com.vehicle.assessment.domain.Vehicle;
import com.vehicle.assessment.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/vehicles")
@Api(tags = "Vehicle Application")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @ApiOperation(value = "Retrieving all vehicle data from server")
    @RequestMapping(value = "/",produces = "application/json",method = RequestMethod.GET)
    public ResponseEntity<?> get() throws Exception {
        return new ResponseEntity<>(vehicleService.getAll().call(),HttpStatus.OK);
    }

    @GetMapping(produces = "application/json",value = "vehicle/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable UUID id) throws Exception {
        Callable<Vehicle> byId = vehicleService.getById(id);
        Vehicle call = byId.call();
        return new ResponseEntity<>(call,HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json",produces = "application/json")
    public Vehicle save(@RequestBody Vehicle vehicle) throws Exception {
        return vehicleService.save(vehicle);
    }
}
