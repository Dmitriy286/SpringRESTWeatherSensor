package org.weathersensor.SpringRESTWeatherSensor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.impl.SensorsServiceImpl;

@Component
public class SensorValidator implements Validator {
    private final SensorsServiceImpl sensorsService;

    @Autowired
    public SensorValidator(SensorsServiceImpl sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            SensorDTO sensorDTO = (SensorDTO) target;
            if (sensorsService.findByName(sensorDTO.getName()) != null) {
                errors.rejectValue("newName", String.valueOf(HttpStatus.CONFLICT), "Sensor with such name already exists");
            }
        } catch (Exception e) {
            System.out.println("It is not SensorDTO");
        }

    }
}
