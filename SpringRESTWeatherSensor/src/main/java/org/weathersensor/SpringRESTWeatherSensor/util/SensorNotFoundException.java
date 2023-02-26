package org.weathersensor.SpringRESTWeatherSensor.util;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(String message) {
        super(message);
    }
}
