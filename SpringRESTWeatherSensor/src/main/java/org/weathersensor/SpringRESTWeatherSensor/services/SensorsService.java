package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorsRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    @Transactional
    public Sensor update(String name, Sensor updatedSensor) {
        updatedSensor.setName(name);
        sensorsRepository.save(updatedSensor);

        return updatedSensor;
    }


    @Transactional
    public void delete(Sensor sensor) {
        sensorsRepository.delete(sensor);
    }
}
