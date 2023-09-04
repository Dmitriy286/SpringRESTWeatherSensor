package org.weathersensor.SpringRESTWeatherSensor.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.weathersensor.SpringRESTWeatherSensor.dto.SensorDTO;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class SensorControllerAcceptanceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllSensors() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/sensors"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(content().string(equalTo("[{\"name\":\"First\"},{\"name\":\"Second\"}]")));
    }

    @Test
    void shouldReturnFoundByNameService() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/sensors/sensor 1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(equalTo("{\"name\":\"Sensor 1\"}")));
    }

    @Test
    void shouldThrowSensorNotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sensors/notexisted"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Sensor with such name does not exist"));
    }

//    @Test
//    void shouldCreateNewSensor() throws Exception {
//        SensorDTO sensorDTO = new SensorDTO();
//        sensorDTO.setName("TestSensor");
//        mockMvc.perform(MockMvcRequestBuilders.post("/sensors/registration", "TestSensor")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\":\"TestSensor\"}"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(content().string(equalTo("Sensor has been added")));
//    }

    @Test
    void updateSensor() {
    }
}