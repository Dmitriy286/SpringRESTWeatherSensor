package org.weathersensor.SpringRESTWeatherSensor.util;

public class MeasurementNotFoundException extends RuntimeException {
    public MeasurementNotFoundException(String message) {
        super(message);
    }
}
