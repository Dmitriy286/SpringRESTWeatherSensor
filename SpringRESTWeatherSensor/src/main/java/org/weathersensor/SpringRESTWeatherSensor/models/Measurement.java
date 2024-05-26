package org.weathersensor.SpringRESTWeatherSensor.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "measurement")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "raining")
    private Boolean raining;

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
//    @JsonBackReference
    private Sensor sensor;

}
