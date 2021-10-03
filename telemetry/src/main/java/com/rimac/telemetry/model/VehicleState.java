package com.rimac.telemetry.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vehicleState")
public class VehicleState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name="vehicleId", unique=true)
    private String vehicleId;

    @Getter
    @Setter
    private Double averageSpeed;

    @Getter
    @Setter
    private Double odometer;

    @Getter
    @Setter
    private Integer telemetryMessagesCount;

    @Getter
    @Setter
    private Double maxSpeed;

    @Getter
    @Setter
    private Long lastMessageTimestamp;

    @Getter
    @Setter
    private Double numberOfCharges;

    @Getter
    @Setter
    private Double numberOfParkings;

    @Getter
    @Setter
    private Double numberOfUnknownStates;

    @Getter
    @Setter
    private Double numberOfDrivingStates;

    @Getter
    @Setter
    private VehicleStatus status;

    public VehicleState() {
        this.telemetryMessagesCount = 0;
        this.numberOfCharges = 0.0;
        this.numberOfUnknownStates = 0.0;
        this.numberOfDrivingStates = 0.0;
        this.numberOfParkings = 0.0;
        this.maxSpeed = 0.0;
        this.lastMessageTimestamp = (long) 0;
        this.averageSpeed = 0.0;
        this.odometer = 0.0;
    }

    public void AddTelemetry(Telemetry telemetry) {

        this.telemetryMessagesCount++;

        if(telemetry.getIsCharging() == null) {
            if(telemetry.getCurrentSpeed() == null) {
                this.status = VehicleStatus.UNKNOWN;
                this.numberOfUnknownStates++;
                //data too faulty
            }
            telemetry.setIsCharging(0.0);
        }
        else if(telemetry.getIsCharging() == 1) {
            this.status = VehicleStatus.CHARGING;
            this.numberOfCharges++;
        } else if(telemetry.getCurrentSpeed() == null) {
            this.status = VehicleStatus.UNKNOWN;
            this.numberOfUnknownStates++;
        } else if(telemetry.getCurrentSpeed() == 0) {
            this.status = VehicleStatus.PARKING;
            this.numberOfParkings++;
        } else {
            this.status = VehicleStatus.DRIVING;
            this.numberOfDrivingStates++;
            this.maxSpeed = Math.max(this.maxSpeed, telemetry.getCurrentSpeed());
            this.averageSpeed = (this.averageSpeed * (this.numberOfDrivingStates - 1) + telemetry.getCurrentSpeed())
                    / (this.numberOfDrivingStates);
        }

        if(telemetry.getOdometer() != null){
            if(this.odometer != null) {
                if((telemetry.getRecordedAt() < this.lastMessageTimestamp && telemetry.getOdometer() < this.odometer)
                        || (telemetry.getRecordedAt() > this.lastMessageTimestamp && telemetry.getOdometer() > this.odometer)) {
                    System.out.println("Possible violation - odometer got smaller");
                }
            }

            this.odometer = telemetry.getOdometer();
        }

        this.lastMessageTimestamp = Math.max(this.lastMessageTimestamp, telemetry.getRecordedAt());
    }
}

enum VehicleStatus {
    DRIVING,
    CHARGING,
    PARKING,
    UNKNOWN
}
