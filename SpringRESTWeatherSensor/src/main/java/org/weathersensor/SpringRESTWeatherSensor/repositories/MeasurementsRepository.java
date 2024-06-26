package org.weathersensor.SpringRESTWeatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    @Query(value = "SELECT COUNT (*) FROM measurement WHERE raining = true", nativeQuery = true)
    Integer getRainyDaysCount();

    List<Measurement> findBySensorId(Long sensorId);
}
