package com.rimac.telemetry.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueueMessage {
    private String messageId;
    private Date messageDate;

    private String vehicleId;
    private Long recordedAt;
    private Map<String,Double> signalValues;
}
