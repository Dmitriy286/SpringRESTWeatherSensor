package org.weathersensor.SpringRESTWeatherSensor.services;

import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDto;
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDto;

import java.util.List;

public interface SensorsService {

    List<SensorDto> findAll();

    SensorDto findByName(String name);

    void save(SensorDto sensorDTO);

    SensorDto update(UpdatedSensorDto updatedSensorDTO);

    void delete(SensorDto sensorDTO);

}
