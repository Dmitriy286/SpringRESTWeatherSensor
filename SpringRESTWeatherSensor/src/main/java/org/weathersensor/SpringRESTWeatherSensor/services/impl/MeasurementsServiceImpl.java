package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.MeasurementsRepository;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementsService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementsServiceImpl implements MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    public MeasurementsServiceImpl(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    public Measurement findById(int id) {
        Optional<Measurement> measurement = measurementsRepository.findById(id);
        return measurement.orElse(null);
    }

    @Override
    public List<MeasurementDto> getAllMeasurements() {
        System.out.println("Входим в метод:");
        List<Measurement> measurementList = measurementsRepository.findAll();
        System.out.println("Нашли измерения");
        List<MeasurementDto> measurementDtoList = measurementList.stream().map(e -> convertToMeasurementDTO(e)).collect(Collectors.toList());
        System.out.println("Сконвертили");
        return measurementDtoList;
    }

    @Transactional
    public void save(MeasurementDto measurementDTO) {
        Measurement measurement = convertToMeasurement(measurementDTO);

        enrichMeasurement(measurement);

        measurementsRepository.save(measurement);
    }

    @Override
    public int getRainyDaysCount() {
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

    private Measurement convertToMeasurement(MeasurementDto measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        Sensor sensor = sensorsRepository.findByNameIgnoreCase(measurementDTO.getSensorDto().getName()).orElse(null);
        measurement.setSensor(sensor);
        return measurement;
    }

    private MeasurementDto convertToMeasurementDTO(Measurement measurement) {
        MeasurementDto measurementDTO = modelMapper.map(measurement, MeasurementDto.class);
        System.out.println("measurement:");
        System.out.println(measurement);
        System.out.println("measurementDTO:");
        System.out.println(measurementDTO);
        return measurementDTO;
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setTime(new Date());
    }
}
