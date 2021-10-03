package com.rimac.telemetry.services;

import com.rimac.telemetry.model.Telemetry;
import com.rimac.telemetry.repositories.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;

    @Autowired
    public TelemetryService(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    @Transactional
    public Telemetry create(Telemetry telemetry) {
        return telemetryRepository.save(telemetry);
    }

    public Optional<Telemetry> findByVehicleIdAndRecordedAt(String vehicleId, Long recordedAt) {
        return telemetryRepository.findByVehicleIdAndRecordedAt(vehicleId, recordedAt);
    }
}
