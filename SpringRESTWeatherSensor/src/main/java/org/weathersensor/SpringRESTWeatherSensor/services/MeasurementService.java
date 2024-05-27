package org.weathersensor.SpringRESTWeatherSensor.services;

import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;

import java.util.List;

public interface MeasurementService {

    Measurement findById(Integer id);

    List<MeasurementDto> getAllMeasurements();

    void save(MeasurementDto measurementDTO);

    Integer getRainyDaysCount();

    void saveAll(List<MeasurementDto> measurementDtos);

    List<MeasurementDto> getAllMeasurementsBySensorId(Long sensorId);
}
