package com.vehicle.assessment;


import com.vehicle.assessment.controllers.VehicleController;
import com.vehicle.assessment.domain.TransmissionType;
import com.vehicle.assessment.domain.Vehicle;
import com.vehicle.assessment.repository.VehicleRepository;
import com.vehicle.assessment.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AssessmentApplication.class)
@AutoConfigureMockMvc

public class VehicleControllerTest{


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleRepository vehicleRepository;

    @MockBean
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;
    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void should_return_all_vehicles() throws Exception {
        Vehicle vehicle = buildVehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle);
        List<Vehicle> call = vehicleService.getAll().call();
        given(call).willReturn(vehicles);
        MvcResult mvcResult = this.mockMvc.perform(get("/vehicles/"))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse()).andReturn();
        System.out.println(mvcResult.getResponse().getOutputStream());
    }

    private Vehicle buildVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setTransmissionType(TransmissionType.AUTO);
        vehicle.setYear(2018);
        vehicle.setModel("MODEL");
        vehicle.setMake("MAKE");
        return vehicle;
    }

}
