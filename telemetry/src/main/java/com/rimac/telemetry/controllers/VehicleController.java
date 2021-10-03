package com.rimac.telemetry.controllers;

import com.rimac.telemetry.model.VehicleState;
import com.rimac.telemetry.services.VehicleStateService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {
    private final VehicleStateService vehicleStateService;

    @Autowired
    VehicleController(VehicleStateService vehicleStateService) {
        this.vehicleStateService = vehicleStateService;
    }

    @GetMapping("/api/vehicle/{id}")
    @ResponseBody
    VehicleState getVehicleState(@PathVariable("id") String id) throws NotFoundException {

        return vehicleStateService.findByVehicleId(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
