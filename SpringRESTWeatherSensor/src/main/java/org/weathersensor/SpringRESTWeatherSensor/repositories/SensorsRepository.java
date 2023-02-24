package org.weathersensor.SpringRESTWeatherSensor.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
}
