package org.weathersensor.SpringRESTWeatherSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;
import org.weathersensor.SpringRESTWeatherSensor.services.impl.SensorsServiceImpl;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsServiceImpl sensorsService;

    public MeasurementsController(MeasurementsService measurementsService, SensorsServiceImpl sensorsService) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                    BindingResult bindingResult) {
        bindingResult.hasErrors();

        measurementsService.save(measurementDTO);

        return new ResponseEntity<>("Measurement has been added", HttpStatus.CREATED);
    }

}
