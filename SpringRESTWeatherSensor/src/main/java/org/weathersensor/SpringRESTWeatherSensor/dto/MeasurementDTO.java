package org.weathersensor.SpringRESTWeatherSensor.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

public class MeasurementDTO {

    @NotEmpty
    @Size(min = -100, max = 100)
    private float value;
    @NotEmpty
    private boolean raining;
    @NotEmpty
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
