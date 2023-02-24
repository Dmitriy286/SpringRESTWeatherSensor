package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementsService.save(measurement);
        ResponseEntity<String> response = new ResponseEntity<>("Measurement has been added", HttpStatus.CREATED);

        return response;
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        Sensor sensor = sensorsService.findByName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);
        return measurement;
    }
}
