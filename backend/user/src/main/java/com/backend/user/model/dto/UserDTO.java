package com.backend.user.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Instant creationdate;
    private Boolean isverified;
    private Boolean isenabled;
    private Integer roleId;
    private Integer cityId;
}
