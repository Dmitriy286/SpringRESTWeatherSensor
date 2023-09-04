package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

public class AspectExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<NotFoundException> handleNotFoundException(NotFoundException e) {

        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(),
                new Date()
        );

        return (ResponseEntity) new ResponseEntity<SensorErrorResponse>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }
}
