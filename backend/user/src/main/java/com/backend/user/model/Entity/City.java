package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('city_cityid_seq'")
    @Column(name = "cityid", nullable = false)
    private Integer id;

    @Column(name = "cityname", nullable = false, length = 100)
    private String cityname;

    @Column(name = "inseecode", nullable = false, length = 5)
    private String inseecode;

    @Column(name = "postcode", nullable = false, length = 5)
    private String postcode;

}