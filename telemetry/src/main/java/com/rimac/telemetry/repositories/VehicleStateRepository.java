package com.rimac.telemetry.repositories;

import com.rimac.telemetry.model.Telemetry;
import com.rimac.telemetry.model.VehicleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleStateRepository extends JpaRepository<VehicleState, String> {

    Optional<VehicleState> findByVehicleId(String vehicleId);
   /* @Query("select vs from VehicleState where vs.vehicleId = ?1")
    VehicleState getByVehicleId(String vehicleId);*/
}
