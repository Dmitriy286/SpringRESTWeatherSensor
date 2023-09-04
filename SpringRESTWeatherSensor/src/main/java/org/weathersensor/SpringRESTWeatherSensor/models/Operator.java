package org.weathersensor.SpringRESTWeatherSensor.models;

import jakarta.persistence.*;

@Entity
@Table(name = "operator")
public class Operator {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "personal_number")
    private Integer personalNumber;

    public Operator() {
    }

    public Operator(String name, Integer personalNumber) {
        this.name = name;
        this.personalNumber = personalNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Integer personalNumber) {
        this.personalNumber = personalNumber;
    }
}
