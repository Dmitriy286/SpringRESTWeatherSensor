package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;

public class NotFoundException extends ExceptionResponse{

    public NotFoundException(ExceptionModel exceptionModel) {
        super(exceptionModel);
    }
}
