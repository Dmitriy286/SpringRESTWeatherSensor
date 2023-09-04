package org.weathersensor.SpringRESTWeatherSensor.mappers;

import org.mapstruct.Mapper;
import org.weathersensor.SpringRESTWeatherSensor.config.MapStructConfiguration;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

import java.util.List;

@Mapper(config = MapStructConfiguration.class)
public interface OperatorMapper {

    OperatorDTO operatorToOperatorDTO(Operator operator);

    List<OperatorDTO> operatorsToOperatorDTOs(List<Operator> operators);
}
