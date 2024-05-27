package org.weathersensor.SpringRESTWeatherSensor.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.MeasurementNotFoundException;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementService;
import org.weathersensor.SpringRESTWeatherSensor.util.MeasurementValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @GetMapping
    public List<MeasurementDto> getAllMeasurements() {
        List<MeasurementDto> allMeasurements = measurementService.getAllMeasurements();

        return allMeasurements;
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDays() {
        return measurementService.getRainyDaysCount();
    }

    @GetMapping(value="/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ResponseEntity<List<MeasurementDto>>> getAllMeasurementsBySensor(@RequestParam Long sensorId) {

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

        return Mono.fromCallable(() -> measurementService.getAllMeasurementsBySensorId(sensorId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok)
                .concatWith(
                        interval
                                .flatMap(aLong -> Mono.fromCallable(() -> measurementService.getAllMeasurementsBySensorId(sensorId)))
                                .subscribeOn(Schedulers.boundedElastic())
                                .map(ResponseEntity::ok)
                );
    }

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

        measurementService.save(measurementDTO);

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

        measurementService.saveAll(measurementDtos);

        return new ResponseEntity<>("Measurements have been added", HttpStatus.CREATED);
    }

}
