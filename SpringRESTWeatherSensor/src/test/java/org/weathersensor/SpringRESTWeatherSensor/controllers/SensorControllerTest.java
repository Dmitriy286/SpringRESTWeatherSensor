package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.SensorsService;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorValidator;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


//@SpringBootTest
@WebMvcTest(SensorController.class)
//@WebMvcTest
class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorsService sensorsService;

    @MockBean
    private SensorsRepository sensorsRepository;

    @MockBean
    private SensorValidator sensorValidator;

    @MockBean
    private ModelMapper modelMapper;


//    @BeforeAll
//    public void setup() {
//        this.mvc = MockMvcBuilders.standaloneSetup(new SensorController()).build();
//    }

    @Test
    void shouldReturnAllSensors() throws Exception {
        Mockito
                .when(sensorsRepository.findAll())
                .thenReturn(new ArrayList<Sensor>(Arrays.asList(new Sensor("First"), new Sensor("Second"))));

        List<Sensor> sensorList = new ArrayList<>();
        Sensor sensor1 = new Sensor("First");
        Sensor sensor2 = new Sensor("Second");

        SensorDTO sensorDTO1 = new SensorDTO();
        sensorDTO1.setName("First");
        SensorDTO sensorDTO2 = new SensorDTO();
        sensorDTO2.setName("Second");

        sensorList.add(sensor1);
        sensorList.add(sensor2);
        System.out.println("sensorList:");
        System.out.println(sensorList);
        Mockito
                .when(sensorsService.findAll())
                .thenReturn(sensorList);

        Mockito
                .when(modelMapper.map(sensor1, SensorDTO.class))
                        .thenReturn(sensorDTO1);

        Mockito
                .when(modelMapper.map(sensor2, SensorDTO.class))
                .thenReturn(sensorDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/sensors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(equalTo("[{\"name\":\"First\"},{\"name\":\"Second\"}]")));

        verify(sensorsRepository).findAll();
        verify(sensorsService).findAll();
        verify(modelMapper).map(sensor1, SensorDTO.class);
        verify(modelMapper).map(sensor2, SensorDTO.class);

    }

    @Test
    void getSensorByName() {
        SensorsRepository sensorsRepository = Mockito.mock(SensorsRepository.class);
        SensorsService sensorsService = new SensorsService(sensorsRepository);
        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        SensorValidator sensorValidator = Mockito.mock(SensorValidator.class);

        SensorController sensorController = new SensorController(sensorsService, modelMapper, sensorValidator);
        Mockito.when(sensorsService.findByName("Some name")).thenReturn(new Sensor("Some name"));
    //        Optional<Sensor> optionalSensor = Optional.of(new Sensor("Some name"));
    //        Mockito.when(sensorsRepository.findByNameIgnoreCase("Some name")).thenReturn(optionalSensor);
        String expectedName = "Some name";
        Assertions.assertEquals(expectedName, sensorController.getSensorByName("Some name").getName());
    }


//    @Test
//    public void createStudentCourse() throws Exception {
//        Course mockCourse = new Course("1", "Smallest Number", "1",Arrays.asList("1", "2", "3", "4"));
//
//        // studentService.addCourse to respond back with mockCourse
//        Mockito.when(studentService.addCourse(Mockito.anyString(),Mockito.any(Course.class))).thenReturn(mockCourse);
//
//        // Send course as body to /students/Student1/courses
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/students/Student1/courses")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(exampleCourseJson)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
//
//        assertEquals("http://localhost/students/Student1/courses/1",response.getHeader(HttpHeaders.LOCATION));
//    }
    @Test
    void register() {
    }

    @Test
    void updateSensor() {
    }

    @Test
    void deleteSensor() {
    }
}