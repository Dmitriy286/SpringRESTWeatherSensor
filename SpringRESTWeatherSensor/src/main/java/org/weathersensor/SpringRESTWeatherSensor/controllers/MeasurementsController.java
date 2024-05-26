package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.security.OperatorDetails;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.MeasurementNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.util.MeasurementValidator;

import javax.validation.Valid;
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
    public List<MeasurementDto> getAllMeasurements() {
        System.out.println("Пытаемся вернуть все измерения");
        List<MeasurementDto> allMeasurements = measurementsService.getAllMeasurements();
        System.out.println(allMeasurements);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OperatorDetails operatorDetails = (OperatorDetails) authentication.getPrincipal();

//        System.out.println("Person Details");
//        System.out.println(operatorDetails.getOperator());
//        System.out.println(authentication.getPrincipal());
//        System.out.println(authentication.getAuthorities());
//        System.out.println(authentication.getCredentials());
//        System.out.println(authentication.getDetails());
//        System.out.println(authentication.isAuthenticated());


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
    public ResponseEntity<String> addNewMeasurement(@RequestBody @Valid MeasurementDto measurementDTO,
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

    @PostMapping("/add-all")
    public ResponseEntity<String> addNewMeasurements(@RequestBody List<MeasurementDto> measurementDtos,
                                                    BindingResult bindingResult) {

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

        measurementsService.saveAll(measurementDtos);

        return new ResponseEntity<>("Measurements have been added", HttpStatus.CREATED);
    }

}
