package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {

    private Integer id;
    private String streetnumber;
    private String streetname;
    private String adressdetails;
    private CityDTO cityDTO;
    private UserDTO user;
}
