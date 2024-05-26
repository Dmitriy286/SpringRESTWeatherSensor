package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {
    OPERATOR_NOT_FOUND_EXCEPTION("Operator not found"),
    OPERATORS_NOT_FOUND_EXCEPTION("Operators not found"),
    SENSOR_NOT_FOUND_EXCEPTION("Sensor with id %s not found"),
    MEASUREMENT_NOT_FOUND_EXCEPTION("Measurement with id %s not found");

    private final String message;

    public ExceptionModel buildExceptionModel(Object... args) {
        return ExceptionModel.builder()
                .message(String.format(message, args))
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
    }

}
