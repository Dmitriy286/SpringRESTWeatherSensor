package org.weathersensor.SpringRESTWeatherSensor.mappers;

import org.mapstruct.Mapper;
import org.weathersensor.SpringRESTWeatherSensor.config.MapStructConfiguration;
import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.ExceptionResponse;

@Mapper(config = MapStructConfiguration.class)
public interface ErrorMapper {

    ExceptionModel toExceptionModel(ExceptionResponse exception);
}
