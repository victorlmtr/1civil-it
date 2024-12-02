package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "city_report")
public class CityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_report_id_gen")
    @SequenceGenerator(name = "city_report_id_gen", sequenceName = "city_report_city_report_id_seq", allocationSize = 1)
    @Column(name = "city_report_id", nullable = false)
    private Integer id;

    @Column(name = "city_name", nullable = false, length = 100)
    private String cityName;

    @Column(name = "postcode", nullable = false, length = 5)
    private String postcode;

    @Column(name = "insee_code", nullable = false, length = 5)
    private String inseeCode;

    @OneToMany(mappedBy = "cityReport")
    private Set<Location> locations = new LinkedHashSet<>();

}
