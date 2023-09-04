package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.ExceptionEnum;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.NotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.mappers.OperatorMapper;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;
import org.weathersensor.SpringRESTWeatherSensor.repositories.OperatorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.specifications.OperatorsSpecifications;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorsService {

    private final OperatorsRepository operatorsRepository;
    private final OperatorMapper operatorMapper;

    @Override
    public OperatorDTO getById(Long operatorId) {
        Operator foundOperator = operatorsRepository.findById(operatorId)
                //TODO: динамический id
                .orElseThrow(() -> new NotFoundException(ExceptionEnum.OPERATOR_NOT_FOUND_EXCEPTION.getMessage()));

        return operatorMapper.operatorToOperatorDTO(foundOperator);
    }

    @Override
    public List<OperatorDTO> getByNameOrNumber(String name, Long personalNumber) {
        OperatorsSpecifications filter = new OperatorsSpecifications();
        List<Operator> operators = operatorsRepository.findAll(filter.getSpecification(name, personalNumber));

        if (operators.isEmpty()) {
            throw new NotFoundException(ExceptionEnum.OPERATOR_NOT_FOUND_EXCEPTION.getMessage());
        }

        return operatorMapper.operatorsToOperatorDTOs(operators);
    }
}
