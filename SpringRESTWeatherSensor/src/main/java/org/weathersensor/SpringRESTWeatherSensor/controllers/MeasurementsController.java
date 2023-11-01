package org.weathersensor.SpringRESTWeatherSensor.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDTO;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.MeasurementNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.util.MeasurementValidator;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;
    private final MeasurementValidator measurementValidator;

//    @PersistenceContext


    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.measurementValidator = measurementValidator;
    }

//    @GetMapping
//    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements() {
//        List<MeasurementDTO> measurementList = measurementsService.getAllMeasurements();
//
//        return new ResponseEntity<>(measurementList, HttpStatus.OK);
//    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements() {
        System.out.println("Пытаемся вернуть все измерения");
        List<MeasurementDTO> allMeasurements = measurementsService.getAllMeasurements();
        System.out.println(allMeasurements);
        return allMeasurements;
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDays() {
        return measurementsService.getRainyDaysCount();
    }






    //    @GetMapping("/{sensorId}")
    //    public List<MeasurementDTO> getSensorByName(@PathVariable("sensorId") int sensorId) {
    //        return sensorsService.findByName(name);
    //    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                    BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" -- ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotFoundException(errorMessage.toString());
        }

        measurementsService.save(measurementDTO);

        return new ResponseEntity<>("Measurement has been added", HttpStatus.CREATED);
    }

}
