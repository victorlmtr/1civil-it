package com.backend.user.model.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Integer id;
    private String streetnumber;
    private String streetname;
    private String adressdetails;
    private Integer cityId;
    private Integer userId;
}
