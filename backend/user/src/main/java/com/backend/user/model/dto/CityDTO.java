package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO implements Serializable {

    private Integer id;
    private String cityname;
    private String inseecode;
    private String postcode;
}
