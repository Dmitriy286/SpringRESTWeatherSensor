package org.weathersensor.SpringRESTWeatherSensor.exceptions.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.NotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.SensorErrorResponse;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
@RequiredArgsConstructor
public class AspectExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> handleNotFoundException(NotFoundException e) {

        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(),
                new Date()
        );

        return (ResponseEntity) new ResponseEntity<SensorErrorResponse>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }
}
