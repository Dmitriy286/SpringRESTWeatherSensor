package org.weathersensor.SpringRESTWeatherSensor.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementService;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;

@Component
@Slf4j
@RequiredArgsConstructor
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        try {
            MeasurementDto measurementDTO = (MeasurementDto) target;
            if (sensorsService.findByName(measurementDTO.getSensorDto().getName()) == null) {
                errors.rejectValue("sensor", String.valueOf(HttpStatus.NOT_FOUND), "Sensor with such name does not exist");
            }
        } catch (Exception e) {
            log.error("It is not MeasurementDTO");
        }
    }
}
