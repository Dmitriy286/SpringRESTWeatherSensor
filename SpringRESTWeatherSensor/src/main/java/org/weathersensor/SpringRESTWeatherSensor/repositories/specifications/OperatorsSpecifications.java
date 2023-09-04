package org.weathersensor.SpringRESTWeatherSensor.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;

public class OperatorsSpecifications {

    public Specification<Operator> operatorNameEqual(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public Specification<Operator> personalNumberEqual(Long personalNumber) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("personalNumber"), personalNumber);
    }

    public Specification<Operator> getSpecification(String name, Long personalNumber) {
        return Specification
                .where(name == null ? null : operatorNameEqual(name))
                .or(personalNumber == null ? null : personalNumberEqual(personalNumber));
    }
}

