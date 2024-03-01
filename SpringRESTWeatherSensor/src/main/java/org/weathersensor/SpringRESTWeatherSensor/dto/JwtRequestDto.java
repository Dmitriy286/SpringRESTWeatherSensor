package org.weathersensor.SpringRESTWeatherSensor.dto;

import lombok.Getter;

@Getter
public class JwtRequestDto {

    private String username;
    private String password;
}
