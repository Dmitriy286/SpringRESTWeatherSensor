package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {
    OPERATOR_NOT_FOUND_EXCEPTION("Operator not found"),
    OPERATORS_NOT_FOUND_EXCEPTION("Operators not found"),
    SENSOR_NOT_FOUND_EXCEPTION("Sensor with id %s not found");

    private final String message;

}
