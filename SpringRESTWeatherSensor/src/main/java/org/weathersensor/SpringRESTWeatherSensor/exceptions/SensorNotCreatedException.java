package org.weathersensor.SpringRESTWeatherSensor.exceptions;

public class SensorNotCreatedException extends RuntimeException {

    public SensorNotCreatedException(String message) {
        super(message);
    }
}
