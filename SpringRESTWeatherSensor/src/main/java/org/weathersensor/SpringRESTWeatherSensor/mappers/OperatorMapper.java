package org.weathersensor.SpringRESTWeatherSensor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.weathersensor.SpringRESTWeatherSensor.config.MapStructConfiguration;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

import java.util.List;

@Mapper(config = MapStructConfiguration.class)
public interface OperatorMapper {

    OperatorDTO operatorToOperatorDTO(Operator operator);

    List<OperatorDTO> operatorsToOperatorDTOs(List<Operator> operators);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Operator operatorDtoToOperator(OperatorDTO operatorDTO);
}
