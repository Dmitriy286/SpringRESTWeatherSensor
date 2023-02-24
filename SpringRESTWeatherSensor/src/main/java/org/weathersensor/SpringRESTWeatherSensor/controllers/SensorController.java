package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody SensorDTO sensorDTO) {
        //todo BindingResult
        sensorsService.save(convertToSensor(sensorDTO));

        ResponseEntity<String> response = new ResponseEntity<>("Sensor has been added", HttpStatus.CREATED);

        return response;
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        return sensor;
    }
}
