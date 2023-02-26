package org.weathersensor.SpringRESTWeatherSensor.util;

public class SensorNotCreatedException extends RuntimeException {

    public SensorNotCreatedException(String message) {
        super(message);
    }
}
