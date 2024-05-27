package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.MeasurementsRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Measurement findById(Integer id) {
        Optional<Measurement> measurement = measurementsRepository.findById(id);

        return measurement.orElse(null);
    }

    //TODO: возврат в обертке
    @Override
    public List<MeasurementDto> getAllMeasurements() {
        List<Measurement> measurements = measurementsRepository.findAll();

        return measurements.stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(MeasurementDto measurementDTO) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        enrichMeasurement(measurement);

        measurementsRepository.save(measurement);
    }

    @Override
    public Integer getRainyDaysCount() {

        return measurementsRepository.getRainyDaysCount();
    }

    @Override
    @Transactional
    public void saveAll(List<MeasurementDto> measurementDtos) {
        var measurementList = measurementDtos.stream()
                .map(this::convertToMeasurement)
                .peek(this::enrichMeasurement)
                .toList();

        measurementsRepository.saveAll(measurementList);
    }

    @Override
    public List<MeasurementDto> getAllMeasurementsBySensorId(Long sensorId) {
        List<Measurement> measurements = measurementsRepository.findBySensorId(sensorId);

        return measurements.stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    private Measurement convertToMeasurement(MeasurementDto measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);

        String sensorName = measurementDTO.getSensorDto().getName();
        Sensor sensor = sensorsRepository.findByNameIgnoreCase(sensorName)
                .orElse(null);
        measurement.setSensor(sensor);

        return measurement;
    }

    private MeasurementDto convertToMeasurementDTO(Measurement measurement) {

        return modelMapper.map(measurement, MeasurementDto.class);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setTime(new Date());
    }
}
