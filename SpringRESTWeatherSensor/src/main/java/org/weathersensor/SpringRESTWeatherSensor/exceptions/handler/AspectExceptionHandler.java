package org.weathersensor.SpringRESTWeatherSensor.exceptions.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.weathersensor.SpringRESTWeatherSensor.dto.ExceptionModel;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.ConflictException;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.NotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.mappers.ErrorMapper;

@ControllerAdvice
@RequiredArgsConstructor
public class AspectExceptionHandler {

    private final ErrorMapper errorMapper;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionModel> handleNotFoundException(NotFoundException exception) {
        return createExceptionResponse(errorMapper.toExceptionModel(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<ExceptionModel> handleException(ConflictException exception) {
        return createExceptionResponse(errorMapper.toExceptionModel(exception), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ExceptionModel> createExceptionResponse(ExceptionModel exceptionModel, HttpStatus httpStatus) {
        return new ResponseEntity<>(exceptionModel, httpStatus);
    }
}
