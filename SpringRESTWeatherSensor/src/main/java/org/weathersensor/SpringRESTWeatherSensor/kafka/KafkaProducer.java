package org.weathersensor.SpringRESTWeatherSensor.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.weathersensor.SpringRESTWeatherSensor.dto.MeasurementDto;
import org.weathersensor.SpringRESTWeatherSensor.services.MeasurementService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final MeasurementService measurementService;

    @Bean
    public CommandLineRunner commandLineRunner(KafkaTemplate<String, Object> kafkaTemplate) {
        return args -> {
            List<MeasurementDto> allMeasurements = measurementService.getAllMeasurements();

            allMeasurements.forEach(measurement -> {
                kafkaTemplate.send("measurements", measurement);
            });
//            kafkaTemplate.send("poems_4", "1", "first_from_app");
        };
    }
}
