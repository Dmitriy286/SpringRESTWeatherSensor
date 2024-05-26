package org.weathersensor.SpringRESTWeatherSensor.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CredentialsError {

    private Integer status;
    private String message;
    private Date timeStamp;

    public CredentialsError(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date();
    }
}
