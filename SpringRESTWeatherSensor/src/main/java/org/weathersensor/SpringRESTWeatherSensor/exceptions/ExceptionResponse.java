package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import lombok.Getter;
import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;

@Getter
public class ExceptionResponse extends RuntimeException {

    private String message;
    private String timestamp;

    public ExceptionResponse(ExceptionModel exceptionModel) {
        super(exceptionModel.getMessage());

        this.message = exceptionModel.getMessage();
        this.timestamp = exceptionModel.getTimestamp();
    }
}
