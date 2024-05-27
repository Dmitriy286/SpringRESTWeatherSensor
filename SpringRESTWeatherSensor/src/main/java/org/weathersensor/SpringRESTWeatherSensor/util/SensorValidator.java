package org.weathersensor.SpringRESTWeatherSensor.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;

@Component
@Slf4j
@RequiredArgsConstructor
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        try {
            SensorDto sensorDTO = (SensorDto) target;
            if (sensorsService.findByName(sensorDTO.getName()) != null) {
                errors.rejectValue("newName", String.valueOf(HttpStatus.CONFLICT), "Sensor with such name already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();

            log.error("It is not SensorDTO");
        }
    }
}
