package org.weathersensor.SpringRESTWeatherSensor.dto;

import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

public class MeasurementDTO {

    private float value;
    private boolean raining;
    private Sensor sensor;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor='" + sensor + '\'' +
                '}';
    }
}
