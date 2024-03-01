package org.weathersensor.SpringRESTWeatherSensor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.weathersensor.SpringRESTWeatherSensor.dto.JwtRequestDto;
import org.weathersensor.SpringRESTWeatherSensor.dto.JwtResponseDto;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.CredentialsError;
import org.weathersensor.SpringRESTWeatherSensor.services.OperatorsService;
import org.weathersensor.SpringRESTWeatherSensor.util.JWTUtils;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OperatorsService operatorsService;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        System.out.println("Creds: ");
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());

        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>(new CredentialsError(HttpStatus.UNAUTHORIZED.value(), "Incorret login or password"),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = operatorsService.loadUserByUsername(request.getUsername());

        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseDto(token));
    }
}
