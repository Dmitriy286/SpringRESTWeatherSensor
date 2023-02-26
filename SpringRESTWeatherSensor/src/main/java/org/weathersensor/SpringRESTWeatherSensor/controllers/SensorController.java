package org.weathersensor.SpringRESTWeatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorErrorResponse;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorNotCreatedException;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorValidator;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid SensorDTO sensorDTO,
                                           BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);

        System.out.println(bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage
                        .append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMessage.toString());
        }

        sensorsService.save(convertToSensor(sensorDTO));

        ResponseEntity<String> response = new ResponseEntity<>("Sensor has been added", HttpStatus.CREATED);

        return response;
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException exception) {
        SensorErrorResponse response = new SensorErrorResponse(
                exception.getMessage(),
                new Date()
        );
        ResponseEntity<SensorErrorResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.CONFLICT);

        return responseEntity;
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        return sensor;
    }
}
