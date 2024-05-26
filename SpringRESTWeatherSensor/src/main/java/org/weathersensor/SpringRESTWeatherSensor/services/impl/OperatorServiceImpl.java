package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.weathersensor.SpringRESTWeatherSensor.dto.OperatorDTO;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.ExceptionEnum;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.NotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.mappers.OperatorMapper;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;
import org.weathersensor.SpringRESTWeatherSensor.models.Role;
import org.weathersensor.SpringRESTWeatherSensor.repositories.OperatorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.RoleRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.specifications.OperatorsSpecifications;
import org.weathersensor.SpringRESTWeatherSensor.security.OperatorDetails;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;
import org.weathersensor.SpringRESTWeatherSensor.services.RegistrationService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorsService {

    private final OperatorsRepository operatorsRepository;
    private final RoleRepository roleRepository;

    private final OperatorMapper operatorMapper;

//    private final PasswordEncoder passwordEncoder;

    private final RegistrationService registrationService;

    @Override
    public OperatorDTO getById(Long operatorId) {
        var foundOperator = operatorsRepository.findById(operatorId)
                .orElseThrow(() -> new NotFoundException(ExceptionEnum.OPERATOR_NOT_FOUND_EXCEPTION.buildExceptionModel()));


        //test get creds from session
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        System.out.println("===========");
        System.out.println(principal.getAuthorities());
        System.out.println(principal.getUsername());
        System.out.println(principal.getPassword());
        System.out.println("===========");

        OperatorDetails operatorDetails = (OperatorDetails) authentication.getPrincipal();

        System.out.println("===========");
        System.out.println(operatorDetails.getOperator().getName());
        System.out.println(operatorDetails.getOperator().getPersonalNumber());
        System.out.println("===========");

        return operatorMapper.operatorToOperatorDTO(foundOperator);
    }

    @Override
    public List<OperatorDTO> getByNameOrNumber(String name, Long personalNumber) {
        var filter = new OperatorsSpecifications();
        var operators = operatorsRepository.findAll(filter.getSpecification(name, personalNumber));

        if (operators.isEmpty()) {
            throw new NotFoundException(ExceptionEnum.OPERATOR_NOT_FOUND_EXCEPTION.buildExceptionModel());
        }

        return operatorMapper.operatorsToOperatorDTOs(operators);
    }

    @Override
    public void create(OperatorDTO operatorDTO) {
        Operator newOperator = operatorMapper.operatorDtoToOperator(operatorDTO);

        Role role = roleRepository.findByName("ROLE_USER")
                .orElse(null);

        newOperator.setRoles(Set.of(role));

        registrationService.register(newOperator);

        operatorsRepository.save(newOperator);
    }

    @Override
    public Operator getByUserName(String username) {
        return operatorsRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ExceptionEnum.OPERATOR_NOT_FOUND_EXCEPTION.buildExceptionModel()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Operator operator = getByUserName(username);

        return new OperatorDetails(operator);
    }
}
