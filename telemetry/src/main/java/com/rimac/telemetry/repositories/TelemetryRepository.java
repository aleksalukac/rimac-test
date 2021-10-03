package com.rimac.telemetry.repositories;

import com.rimac.telemetry.model.Telemetry;
import com.rimac.telemetry.model.VehicleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, String> {

    Optional<Telemetry> findByVehicleIdAndRecordedAt(String vehicleId, Long recordedAt);
}
