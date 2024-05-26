package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;
import org.weathersensor.SpringRESTWeatherSensor.services.RegistrationService;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

//    private final PasswordEncoder passwordEncoder;

    private final ApplicationContext context;

    @Override
    public void register(Operator operator) {

        PasswordEncoder passwordEncoder = (PasswordEncoder) context.getBean("passwordEncoder");

        String encode = passwordEncoder.encode(operator.getPassword());
        operator.setPassword(encode);

        System.out.println("Encoded: " + passwordEncoder.encode("5"));
    }
}
