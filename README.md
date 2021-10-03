# rimac-test
App to get telemetry from cars via RabbitMQ and store it in SQL server. Exposes vehicle status via api/vehicle

# how to start
App was built in java spring boot and uses rabbitmq and sql server that run in docker.
Instructions on how to start those apps in docker are in [this file](https://github.com/aleksalukac/rimac-test/blob/main/telemetry/docker%20instructions.txt)

JSON requests should be sent at POST: http://localhost:9000/publish and they should look like this
```json
{
     "vehicleId":"2",
     "recordedAt":1453,
     "signalValues": {"currentSpeed":110,"odometer":2000,"drivingTime":56000,"isCharging":0}
}
```

Checking vehicle's status can be done using http://localhost:9000/api/vehicles/{id} where {id} is vehicle's id (for example: http://localhost:9000/api/vehicles/2)

___

* If vehicleid is missing from the sample, error will be displayed.
* If recordedAt is missing from the sample, 0 will be put as recordedAt.
* If currentSpeed is missing, it will be set to 0.
* If vehicle has speed but it is charging, vehicle speed will be set to 0 and alert note will be displayed. 
* If charging status is missing, it will be set to 0 (not charging).
* If odometer is missing, vehicle status will display the last odometer available, same with driving time.

Fraud alert: if odometer is smaller that previous and the sample has been recorded after the previous one, alert of possible fraud or error is displayed.

---
Test examples are available in [input examples](input-examples) directory. Input 5 should not work because of the same recordedAt value as one of the previous examples.