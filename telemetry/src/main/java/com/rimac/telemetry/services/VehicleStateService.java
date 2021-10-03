package com.rimac.telemetry.services;

import com.rimac.telemetry.model.Telemetry;
import com.rimac.telemetry.model.VehicleState;
import com.rimac.telemetry.repositories.TelemetryRepository;
import com.rimac.telemetry.repositories.VehicleStateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleStateService {

    private final VehicleStateRepository vehicleStateRepository;

    @Transactional
    public VehicleState save(VehicleState vehicleState) {
        return vehicleStateRepository.save(vehicleState);
    }

    @Transactional
    public void add(Telemetry telemetry) {
        var vehicleStateOptional = vehicleStateRepository.findByVehicleId(telemetry.getVehicleId());

        VehicleState vehicleState;
        if(!vehicleStateOptional.isPresent()) {
            vehicleState = new VehicleState();
            vehicleState.setVehicleId(telemetry.getVehicleId());
        } else {
            vehicleState = vehicleStateOptional.get();
        }
        vehicleState.AddTelemetry(telemetry);

        save(vehicleState);
    }

    public Optional<VehicleState> findByVehicleId(String id) {
        return vehicleStateRepository.findByVehicleId(id);
    }
}
