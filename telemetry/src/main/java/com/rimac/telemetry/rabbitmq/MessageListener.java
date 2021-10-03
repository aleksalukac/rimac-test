package com.rimac.telemetry.rabbitmq;

import com.rimac.telemetry.model.Telemetry;
import com.rimac.telemetry.repositories.TelemetryRepository;
import com.rimac.telemetry.services.TelemetryService;
import com.rimac.telemetry.services.VehicleStateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @Autowired
    private TelemetryService telemetryService;
    @Autowired
    private VehicleStateService vehicleStateService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(QueueMessage message) {
        System.out.println(message);

        if(message.getVehicleId() == null) {
            System.out.println("Error - vehicleId missing");
            return;
        }
        Telemetry telemetry = new Telemetry(message);

        //check if the same sample is already in the database
        var result = telemetryService.findByVehicleIdAndRecordedAt(telemetry.getVehicleId(), telemetry.getRecordedAt());
        if(result.isPresent()) {
            System.out.println("Error - duplicated telemetry sample");
            return;
        }
        telemetryService.create(telemetry);
        vehicleStateService.add(telemetry);
    }
}
