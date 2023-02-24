package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public Sensor findByName(String name) {
        return sensorsRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
