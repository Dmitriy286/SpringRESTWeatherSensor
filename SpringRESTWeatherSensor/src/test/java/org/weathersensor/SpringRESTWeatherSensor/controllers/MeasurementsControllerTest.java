package org.weathersensor.SpringRESTWeatherSensor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.weathersensor.SpringRESTWeatherSensor.models.Measurement;
import org.weathersensor.SpringRESTWeatherSensor.models.Sensor;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementsControllerTest {

    @Test
    void getAllMeasurements() {
    }

    @Test
    void addNewMeasurement() {
    }

    @Test
    public void givenBidirectionRelation_whenSerializing_thenException() {
        JsonProcessingException exception = Assertions.assertThrows(JsonProcessingException.class, () -> {
            Sensor sensor = new Sensor("test sensor");
            Date time = new Date();
            Measurement measurement = new Measurement(10f, true, time, sensor);
            sensor.setMeasurementList(Arrays.asList(measurement));
            new ObjectMapper().writeValueAsString(measurement);
        });

        String exceptionMessage = "Infinite recursion (StackOverflowError) (through reference chain: org.weathersensor.SpringRESTWeatherSensor.models.Sensor[\"measurementList\"]->java.util.Arrays$ArrayList[0]->org.weathersensor.SpringRESTWeatherSensor.models.Measurement[\"sensor\"]->org.w ...";

        Assertions.assertEquals(JsonProcessingException.class, exception.getClass());

    }

    @Test
    public void givenBidirectionRelation_whenSerializing_thenException_two() {
        Assertions.assertThrows(JsonProcessingException.class, () -> {
            Sensor sensor = new Sensor("test sensor");
            Date time = new Date();
            Measurement measurement = new Measurement(10f, true, time, sensor);
            sensor.setMeasurementList(Arrays.asList(measurement));
            new ObjectMapper().writeValueAsString(measurement);
        });
    }

//    @Test
//    public void givenBidirectionRelation_whenUsingJacksonReferenceAnnotationWithSerialization_thenCorrect() throws JsonProcessingException {
//        final User user = new User(1, "John");
//        final Item item = new Item(2, "book", user);
//        user.addItem(item);
//
//        final String itemJson = new ObjectMapper().writeValueAsString(item);
//        final String userJson = new ObjectMapper().writeValueAsString(user);
//
//        assertThat(itemJson, containsString("book"));
//        assertThat(itemJson, not(containsString("John")));
//
//        assertThat(userJson, containsString("John"));
//        assertThat(userJson, containsString("userItems"));
//        assertThat(userJson, containsString("book"));
//    }
}