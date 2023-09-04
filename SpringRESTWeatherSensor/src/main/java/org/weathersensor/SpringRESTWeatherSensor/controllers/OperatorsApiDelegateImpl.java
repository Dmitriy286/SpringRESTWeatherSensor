package org.weathersensor.SpringRESTWeatherSensor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.weathersensor.SpringRESTWeatherSensor.controllers.api.OperatorsApiDelegate;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OperatorsApiDelegateImpl implements OperatorsApiDelegate {

    private final OperatorsService operatorsService;

    @Override
    public ResponseEntity<OperatorDTO> getOperator(Long operatorId) {
        return ResponseEntity.ok(operatorsService.getById(operatorId));
    }

    @Override
    public ResponseEntity<List<OperatorDTO>> getOperators(String name, Long personalNumber) {
        return ResponseEntity.ok(operatorsService.getByNameOrNumber(name, personalNumber));
    }
}
