package org.weathersensor.SpringRESTWeatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

@Repository
public interface OperatorsRepository extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
}
