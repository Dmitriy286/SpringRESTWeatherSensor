package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDto;
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.SensorNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorsServiceImpl implements SensorsService {

    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    public List<SensorDto> findAll() {
        return sensorsRepository.findAll().stream().map(this::convertToSensorDTO).toList();
    }

    public SensorDto findByName(String name) {
        var foundSensor = sensorsRepository.findByNameIgnoreCase(name);

        return foundSensor.map(this::convertToSensorDTO).orElse(null);
    }

    @Transactional
    public void save(SensorDto sensorDTO) {
        sensorsRepository.save(convertToSensorDTO(sensorDTO));
    }

    @Transactional
    public SensorDto update(UpdatedSensorDto updatedSensorDTO) {
        Sensor updatedSensor = sensorsRepository.findByNameIgnoreCase(updatedSensorDTO.getName()).orElse(null);

        if (updatedSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }
        updatedSensor.setName(updatedSensorDTO.getNewName());

        sensorsRepository.save(updatedSensor);

        return convertToSensorDTO(updatedSensor);
    }

    @Transactional
    public void delete(SensorDto sensorDTO) {
        Sensor deletedSensor = sensorsRepository.findByNameIgnoreCase(sensorDTO.getName()).orElse(null);

        if (deletedSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }

        sensorsRepository.delete(convertToSensorDTO(sensorDTO));
    }

    private Sensor convertToSensorDTO(SensorDto sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);

        return sensor;
    }

    private Sensor convertToSensor(UpdatedSensorDto updatedSensorDTO) {
        Sensor sensor = modelMapper.map(updatedSensorDTO, Sensor.class);

        Sensor currentSensor = sensorsRepository.findByNameIgnoreCase(updatedSensorDTO.getName()).orElse(null);
        if (currentSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }

        Long id = currentSensor.getId();
        sensor.setId(id);

        return sensor;
    }

    private SensorDto convertToSensorDTO(Sensor sensor) {
        SensorDto sensorDTO = modelMapper.map(sensor, SensorDto.class);

        return sensorDTO;
    }
}
