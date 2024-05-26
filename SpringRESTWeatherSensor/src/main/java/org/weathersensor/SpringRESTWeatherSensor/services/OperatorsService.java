package org.weathersensor.SpringRESTWeatherSensor.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

import java.util.List;

public interface OperatorsService extends UserDetailsService {

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

    /**
     * Creates new operator.
     *
     * @param operatorDTO {@link OperatorDTO} Operator data transfer object
     */
    void create(OperatorDTO operatorDTO);

    /**
     * Gets operator by username.
     *
     * @param username Operator's username
     * @return {@link Operator} Operator entity.
     */
    Operator getByUserName(String username);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
