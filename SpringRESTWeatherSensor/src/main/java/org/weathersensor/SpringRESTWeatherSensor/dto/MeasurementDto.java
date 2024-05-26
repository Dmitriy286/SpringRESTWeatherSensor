package org.weathersensor.SpringRESTWeatherSensor.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MeasurementDto {

    @NotEmpty
    @Size(min = -100, max = 100)
    private float value;
    @NotEmpty
    private boolean raining;
    @NotEmpty
    private SensorDTO sensorDto;
}
