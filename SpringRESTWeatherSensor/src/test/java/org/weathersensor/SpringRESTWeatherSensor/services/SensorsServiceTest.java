package org.weathersensor.SpringRESTWeatherSensor.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class SensorsServiceTest {

//    @Mock
    private SensorsRepository sensorsRepository;

//    @InjectMocks
    private SensorsService sensorsService;

    @BeforeEach
    public void setUp() {
        sensorsRepository = Mockito.mock(SensorsRepository.class);
        sensorsService = new SensorsService(sensorsRepository);
    }

    @Test
    void shouldReturnSensorList() {
        List<Sensor> sensorList = getSensorList();

        Mockito.when(sensorsRepository.findAll()).thenReturn(sensorList);

        List<Sensor> expectedSensorList = getSensorList();
        List<Sensor> resultSensorList = sensorsService.findAll();

        Assertions.assertNotNull(resultSensorList);
        Assertions.assertEquals(expectedSensorList.toString(), resultSensorList.toString());
    }

    @Test
    void findByName() {
        List<Sensor> sensorList = getSensorList();
        Sensor expectedSensor = sensorList.get(0);
        Mockito.when(sensorsRepository.findByNameIgnoreCase("First")).thenReturn(Optional.of(new Sensor("First")));

        Sensor resultSensor = sensorsService.findByName("First");

        Assertions.assertNotNull(resultSensor);
        Assertions.assertEquals(expectedSensor.getName(), resultSensor.getName());
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private List<Sensor> getSensorList() {
        List<Sensor> sensorList = new ArrayList<>();
        Sensor sensor1 = new Sensor("First");
        Sensor sensor2 = new Sensor("Second");

        SensorDTO sensorDTO1 = new SensorDTO();
        sensorDTO1.setName("First");
        SensorDTO sensorDTO2 = new SensorDTO();
        sensorDTO2.setName("Second");

        sensorList.add(sensor1);
        sensorList.add(sensor2);

        return sensorList;
    }
}