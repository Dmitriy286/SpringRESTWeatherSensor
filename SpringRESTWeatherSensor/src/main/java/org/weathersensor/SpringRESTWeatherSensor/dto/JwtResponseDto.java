package org.weathersensor.SpringRESTWeatherSensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDto {

    private String token;
}
