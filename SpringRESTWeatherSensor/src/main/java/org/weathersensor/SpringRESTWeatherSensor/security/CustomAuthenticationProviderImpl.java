package org.weathersensor.SpringRESTWeatherSensor.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProviderImpl implements AuthenticationProvider {

    private final OperatorsService operatorsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();

        var userDetails = operatorsService.loadUserByUsername(username);

        var password = authentication.getCredentials().toString();

        if (!Objects.equals(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
