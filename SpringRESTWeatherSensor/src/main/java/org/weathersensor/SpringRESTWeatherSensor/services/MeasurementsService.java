package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;

import java.util.Date;
import java.util.Optional;

public interface MeasurementsService {

    public Measurement findById(int id);

    public void save(MeasurementDTO measurementDTO);

}
