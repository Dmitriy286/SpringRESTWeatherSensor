package org.weathersensor.SpringRESTWeatherSensor.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.impl.SensorsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@ExtendWith(MockitoExtension.class)
class SensorsServiceTest {

//    @Mock
    private SensorsRepository sensorsRepository;

//    @InjectMocks
    private SensorsServiceImpl sensorsService;

    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        sensorsRepository = Mockito.mock(SensorsRepository.class);
        modelMapper = new ModelMapper();
        sensorsService = new SensorsServiceImpl(sensorsRepository, modelMapper);
    }

    @Test
    void shouldReturnSensorList() {
        List<Sensor> sensorList = getSensorList();

        Mockito.when(sensorsRepository.findAll()).thenReturn(sensorList);

        List<Sensor> expectedSensorList = getSensorList();
        List<SensorDto> resultSensorDtoList = sensorsService.findAll();

        Assertions.assertNotNull(resultSensorDtoList);
        Assertions.assertEquals(expectedSensorList.toString(), resultSensorDtoList.toString());
    }

    @Test
    void findByName() {
        List<Sensor> sensorList = getSensorList();
        Sensor expectedSensor = sensorList.get(0);
        Mockito.when(sensorsRepository.findByNameIgnoreCase("First")).thenReturn(Optional.of(new Sensor("First")));

        SensorDto resultSensorDto = sensorsService.findByName("First");

        Assertions.assertNotNull(resultSensorDto);
        Assertions.assertEquals(expectedSensor.getName(), resultSensorDto.getName());
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

        SensorDto sensorDto1 = new SensorDto();
        sensorDto1.setName("First");
        SensorDto sensorDto2 = new SensorDto();
        sensorDto2.setName("Second");

        sensorList.add(sensor1);
        sensorList.add(sensor2);

        return sensorList;
    }
}