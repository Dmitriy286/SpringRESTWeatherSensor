package org.weathersensor.SpringRESTWeatherSensor.constants;

import jakarta.persistence.Access;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationConstants {

    public static String FORMATTER = "%%%s%%";

    public static String NAME_FIELD = "name";
    public static String PERSONAL_NUMBER_FIELD = "personalNumber";
}
