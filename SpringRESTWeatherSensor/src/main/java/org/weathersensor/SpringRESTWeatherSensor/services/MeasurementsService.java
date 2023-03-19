package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MeasurementsService {

    Measurement findById(int id);

    List<MeasurementDTO> getAllMeasurements();

    void save(MeasurementDTO measurementDTO);

    int getRainyDaysCount();

}
