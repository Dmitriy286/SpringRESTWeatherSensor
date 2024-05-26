package org.weathersensor.SpringRESTWeatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByNameIgnoreCase(String name);

    void deleteByNameIgnoreCase(String name);

}
