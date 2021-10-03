package com.rimac.telemetry.model;


import com.rimac.telemetry.rabbitmq.QueueMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "telemetry")
@NoArgsConstructor
@AllArgsConstructor
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    private String vehicleId;

    @Getter
    @Setter
    private Long recordedAt;

    private Double currentSpeed;

    public void setCurrentSpeed(double speed) {
        this.currentSpeed = speed;
    }

    public Double getCurrentSpeed() {
        return this.currentSpeed;
    }

    @Getter
    @Setter
    private Double odometer;

    @Getter
    @Setter
    private Double drivingTime;

    private Double isCharging;

    public void setIsCharging(Double isCharging) {
        this.isCharging = isCharging;
    }

    public Double getIsCharging() {
        return this.isCharging;
    }

    public Telemetry(QueueMessage message) {
        super();

        this.vehicleId = message.getVehicleId();
        this.recordedAt = Math.max(0, message.getRecordedAt());

        var signalValues = message.getSignalValues();

        if(signalValues.containsKey("currentSpeed")) {
            this.currentSpeed = Math.max(0, signalValues.get("currentSpeed"));
        }

        if(signalValues.containsKey("odometer")) {
            this.odometer = Math.max(0, signalValues.get("odometer"));
        }

        if(signalValues.containsKey("drivingTime")) {
            this.drivingTime = Math.max(0, signalValues.get("drivingTime"));
        }

        if(signalValues.containsKey("isCharging")) {
            this.isCharging = signalValues.get("isCharging") == 1.0 ? 1.0 : 0.0;
        }

        if(this.isCharging != null){
            if(this.isCharging == 1 && this.currentSpeed > 0) {
                currentSpeed = 0.0;
                System.out.println("Possible error - speed not zero while charging");
            }
        }
    }
}
