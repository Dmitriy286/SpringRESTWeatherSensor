package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.repositories.MeasurementsRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
    }

    public Measurement findById(int id) {
        Optional<Measurement> measurement = measurementsRepository.findById(id);
        return measurement.orElse(null);
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);

        measurementsRepository.save(measurement);
    }


    private void enrichMeasurement(Measurement measurement) {
        measurement.setTime(Timestamp.valueOf(LocalDateTime.now()));
    }
}
