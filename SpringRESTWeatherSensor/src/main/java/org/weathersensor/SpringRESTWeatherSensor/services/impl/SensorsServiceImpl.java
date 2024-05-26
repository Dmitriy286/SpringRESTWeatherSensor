package org.weathersensor.SpringRESTWeatherSensor.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.dto.UpdatedSensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.exceptions.SensorNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorsServiceImpl implements SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;


    public SensorsServiceImpl(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    public List<SensorDTO> findAll() {
        return sensorsRepository.findAll().stream().map(this::convertToSensorDTO).toList();
    }

    public SensorDTO findByName(String name) {
        var foundSensor = sensorsRepository.findByNameIgnoreCase(name);

        return foundSensor.map(this::convertToSensorDTO).orElse(null);
    }

    @Transactional
    public void save(SensorDTO sensorDTO) {
        sensorsRepository.save(convertToSensorDTO(sensorDTO));
    }

    @Transactional
    public SensorDTO update(UpdatedSensorDTO updatedSensorDTO) {
        Sensor updatedSensor = sensorsRepository.findByNameIgnoreCase(updatedSensorDTO.getName()).orElse(null);
        if (updatedSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }
        updatedSensor.setName(updatedSensorDTO.getNewName());

        sensorsRepository.save(updatedSensor);

        return convertToSensorDTO(updatedSensor);
    }

    @Transactional
    public void delete(SensorDTO sensorDTO) {
        Sensor deletedSensor = sensorsRepository.findByNameIgnoreCase(sensorDTO.getName()).orElse(null);
        if (deletedSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }
        sensorsRepository.delete(convertToSensorDTO(sensorDTO));
    }

    private Sensor convertToSensorDTO(SensorDTO sensorDTO) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);

        return sensor;
    }

    private Sensor convertToSensor(UpdatedSensorDTO updatedSensorDTO) {
        Sensor sensor = modelMapper.map(updatedSensorDTO, Sensor.class);

        Sensor currentSensor = sensorsRepository.findByNameIgnoreCase(updatedSensorDTO.getName()).orElse(null);
        if (currentSensor == null) {
            throw new SensorNotFoundException("Sensor with such name does not exist");
        }

        Long id = currentSensor.getId();
        sensor.setId(id);

        return sensor;
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        System.out.println("Init converting...");
        System.out.println(sensor);
        SensorDTO sensorDTO = modelMapper.map(sensor, SensorDTO.class);
        System.out.println("sensorDTO:");
        System.out.println(sensorDTO);
        return sensorDTO;
    }
}
