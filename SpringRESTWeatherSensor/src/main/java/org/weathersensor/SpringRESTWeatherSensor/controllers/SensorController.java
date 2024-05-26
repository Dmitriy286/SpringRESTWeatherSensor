package org.weathersensor.SpringRESTWeatherSensor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.SensorNotCreatedException;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.SensorNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sensors")
public class SensorController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorsService.findAll();
    }

    @GetMapping("/{name}")
    public SensorDTO getSensorByName(@PathVariable("name") String name) {
        return sensorsService.findByName(name);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody @Valid SensorDTO sensorDto,
                                           BindingResult bindingResult) {

        sensorValidator.validate(sensorDto, bindingResult);

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
        sensorsService.save(sensorDto);

        sendMessageToKafka(sensorDto);

        return new ResponseEntity<>("Sensor has been added", HttpStatus.CREATED);

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

        sensorsService.update(updatedSensorDTO);

        return new ResponseEntity<>("Sensor has been updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<String> deleteSensor(@RequestBody SensorDTO sensorDTO) {
        sensorsService.delete(sensorDTO);

        return new ResponseEntity<>("Sensor has been deleted", HttpStatus.OK);
    }

    private void sendMessageToKafka(SensorDTO sensorDto) {
        kafkaTemplate.send("new-sensors", sensorDto);
    }
}
