package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;

public class ConflictException extends ExceptionResponse{

    public ConflictException(ExceptionModel exceptionModel) {
        super(exceptionModel);
    }
}
