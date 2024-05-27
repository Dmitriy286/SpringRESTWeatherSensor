package org.weathersensor.SpringRESTWeatherSensor.exceptions;

public class SensorNotFoundException extends RuntimeException{

    public SensorNotFoundException(String message) {
        super(message);
    }
}
