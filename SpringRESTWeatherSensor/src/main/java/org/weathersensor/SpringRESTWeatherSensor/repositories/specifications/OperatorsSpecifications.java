package org.weathersensor.SpringRESTWeatherSensor.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

import static org.weathersensor.SpringRESTWeatherSensor.constants.SpecificationConstants.FORMATTER;
import static org.weathersensor.SpringRESTWeatherSensor.constants.SpecificationConstants.NAME_FIELD;
import static org.weathersensor.SpringRESTWeatherSensor.constants.SpecificationConstants.PERSONAL_NUMBER_FIELD;

public class OperatorsSpecifications {

    public Specification<Operator> operatorNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(NAME_FIELD),
                String.format(FORMATTER, name));
    }

    public Specification<Operator> personalNumberEqual(Long personalNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(PERSONAL_NUMBER_FIELD),
                personalNumber);
    }

    public Specification<Operator> getSpecification(String name, Long personalNumber) {
        return Specification
                .where(name == null ? null : operatorNameLike(name))
                .or(personalNumber == null ? null : personalNumberEqual(personalNumber));
    }
}

