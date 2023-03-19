package org.weathersensor.SpringRESTWeatherSensor.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;


    public MeasurementValidator(MeasurementsService measurementsService, SensorsService sensorsService) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            MeasurementDTO measurementDTO = (MeasurementDTO) target;
            if (sensorsService.findByName(measurementDTO.getSensor().getName()) == null) {
                errors.rejectValue("sensor", String.valueOf(HttpStatus.NOT_FOUND), "Sensor with such name does not exist");
            }
        } catch (Exception e) {
            System.out.println("It is not MeasurementDTO");
        }

    }
}
