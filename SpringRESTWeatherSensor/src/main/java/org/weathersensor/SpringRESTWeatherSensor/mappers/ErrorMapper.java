package org.weathersensor.SpringRESTWeatherSensor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.weathersensor.SpringRESTWeatherSensor.config.MapStructConfiguration;
import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.ExceptionResponse;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.NotFoundException;

@Mapper(config = MapStructConfiguration.class)
public interface ErrorMapper {

    ExceptionModel toExceptionModel(ExceptionResponse exception);
}
