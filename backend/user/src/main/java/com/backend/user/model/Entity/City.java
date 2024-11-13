package com.backend.user.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
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