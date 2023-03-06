package org.weathersensor.SpringRESTWeatherSensor.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MeasureValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
