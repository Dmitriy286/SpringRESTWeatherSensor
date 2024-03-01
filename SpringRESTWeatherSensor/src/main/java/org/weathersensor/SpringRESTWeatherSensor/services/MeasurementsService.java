package org.weathersensor.SpringRESTWeatherSensor.services;

import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;

import java.util.List;

public interface MeasurementsService {

    Measurement findById(int id);

    List<MeasurementDto> getAllMeasurements();

    void save(MeasurementDto measurementDTO);

    int getRainyDaysCount();

    void saveAll(List<MeasurementDto> measurementDtos);
}
