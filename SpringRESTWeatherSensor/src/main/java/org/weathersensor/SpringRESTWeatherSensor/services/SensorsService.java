package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

import java.util.List;

public interface SensorsService {

    List<SensorDTO> findAll();

    SensorDTO findByName(String name);

    void save(SensorDTO sensorDTO);

    SensorDTO update(UpdatedSensorDTO updatedSensorDTO);

    void delete(SensorDTO sensorDTO);

}
