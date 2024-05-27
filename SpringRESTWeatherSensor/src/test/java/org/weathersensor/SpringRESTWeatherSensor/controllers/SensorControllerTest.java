package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDto;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;
import org.weathersensor.SpringRESTWeatherSensor.repositories.SensorsRepository;
import org.weathersensor.SpringRESTWeatherSensor.services.impl.SensorsServiceImpl;
import org.weathersensor.SpringRESTWeatherSensor.util.SensorValidator;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(SensorController.class)
//@WebMvcTest
class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    @InjectMocks
    private SensorsServiceImpl sensorsService;

//    @MockBean
    private SensorsRepository sensorsRepository;

//    @MockBean
//    @InjectMocks
    private SensorValidator sensorValidator;

//    @MockBean
    private ModelMapper modelMapper;

//    @InjectMocks
    private SensorController sensorController;


    @BeforeEach
    public void setup() {
        sensorsRepository = Mockito.mock(SensorsRepository.class);
        modelMapper = new ModelMapper();
        sensorsService = new SensorsServiceImpl(sensorsRepository, modelMapper);
        sensorValidator = new SensorValidator(sensorsService);
        sensorController = new SensorController(sensorsService, sensorValidator);
        mockMvc = MockMvcBuilders.standaloneSetup(new SensorController(sensorsService, sensorValidator)).build();
    }

    @Test
    void shouldReturnAllSensors() throws Exception {
        List<Sensor> sensorList = getSensorList();
        System.out.println("sensorList:");
        System.out.println(sensorList);
        Mockito
                .when(sensorsRepository.findAll())
                .thenReturn(new ArrayList<Sensor>(sensorList));
//                .thenReturn(new ArrayList<Sensor>(Arrays.asList(new Sensor("First"), new Sensor("Second"))));

//        Mockito
//                .when(sensorsService.findAll())
//                .thenReturn(sensorList);

//        Mockito
//                .when(modelMapper.map(sensor1, SensorDTO.class))
//                .thenReturn(sensorDTO1);
//
//        Mockito
//                .when(modelMapper.map(sensor2, SensorDTO.class))
//                .thenReturn(sensorDTO2);

        List<SensorDto> resultSensorDtoList = sensorController.getSensors();

        Assertions.assertEquals(sensorList.get(0).getName(), resultSensorDtoList.get(0).getName());

        mockMvc.perform(MockMvcRequestBuilders.get("/sensors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(equalTo("[{\"name\":\"First\"},{\"name\":\"Second\"}]")));
//
//        verify(sensorsRepository).findAll();
//        verify(sensorsService).findAll();
//        verify(modelMapper).map(sensor1, SensorDTO.class);
//        verify(modelMapper).map(sensor2, SensorDTO.class);

    }

    @Test
    void shouldReturnErrorForUnExistedName() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/sensors/smth");
        MvcResult result = mockMvc.perform(request).andReturn();
//        Assertions.assertEquals("{\"message\":\"Sensor with such name does not exist\",\"timestamp\":" + new Date().getTime() + "}", result.getResponse().getContentAsString());
        Assertions.assertTrue(matchesPattern("\\{\\\"message\\\":\\\"Sensor with such name does not exist\\\",\\\"timestamp\\\":.+}").matches(result.getResponse().getContentAsString()));
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