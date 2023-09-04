package org.weathersensor.SpringRESTWeatherSensor.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
