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
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorErrorResponse;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorNotCreatedException;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorValidator;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public List<SensorDTO> getSensors() {
        List<SensorDTO> sensorDTOList = sensorsService.findAll().stream().map(this::convertToSensorDTO).toList();

        return sensorDTOList;
    }

    @GetMapping("/{name}")
    public SensorDTO getSensorByName(@PathVariable("name") String name) {
        if (sensorsService.findByName(name) == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }
        SensorDTO sensorDTO = convertToSensorDTO(sensorsService.findByName(name));

        return sensorDTO;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid SensorDTO sensorDTO,
                                           BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);

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

    @PatchMapping("/update")
    public ResponseEntity<String> updateSensor(@RequestBody @Valid UpdatedSensorDTO updatedSensorDTO,
                                           BindingResult bindingResult) {
        sensorValidator.validate(updatedSensorDTO, bindingResult);

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

        if (sensorsService.findByName(updatedSensorDTO.getName()) == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }

        sensorsService.update(updatedSensorDTO.getNewName(), convertToSensor(updatedSensorDTO));

        ResponseEntity<String> response = new ResponseEntity<>("Sensor has been updated", HttpStatus.ACCEPTED);

        return response;
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> deleteSensor(@RequestBody SensorDTO sensorDTO) {
        if (sensorsService.findByName(sensorDTO.getName()) == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }
        sensorsService.delete(convertToSensor(sensorDTO));

        ResponseEntity<String> response = new ResponseEntity<>("Sensor has been deleted", HttpStatus.OK);

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

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException exception) {
        SensorErrorResponse response = new SensorErrorResponse(
                exception.getMessage(),
                new Date()
        );
        ResponseEntity<SensorErrorResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        return responseEntity;
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);

        return sensor;
    }

    private Sensor convertToSensor(UpdatedSensorDTO updatedSensorDTO) {
        Sensor sensor = modelMapper.map(updatedSensorDTO, Sensor.class);
        int id = sensorsService.findByName(updatedSensorDTO.getName()).getId();
        sensor.setId(id);

        return sensor;
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        System.out.println("Init converting...");
        System.out.println(sensor);
        SensorDTO sensorDTO = modelMapper.map(sensor, SensorDTO.class);
        System.out.println("sensorDTO:");
        System.out.println(sensorDTO);
        return sensorDTO;
    }
}
