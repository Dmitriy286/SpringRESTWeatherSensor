package org.weathersensor.SpringRESTWeatherSensor.services;

import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;

import java.util.List;

public interface OperatorsService {

    /**
     * Get operators, filtered by name or personal number
     *
     * @param name Operator's name
     * @param personalNumber Operator's personal number
     * @return {@link List<OperatorDTO>} List of data transfer object for Operator's entity.
     */
    List<OperatorDTO> getByNameOrNumber(String name, Long personalNumber);

    /**
     * Get operator by id.
     *
     * @param operatorId Operator's id
     * @return {@link OperatorDTO} Data transfer object for Operator's entity.
     */
    OperatorDTO getById(Long operatorId);
}
