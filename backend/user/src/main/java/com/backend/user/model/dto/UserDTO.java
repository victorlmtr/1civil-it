package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Value
@Builder
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phonenumber;
    private Instant creationdate;
    private Boolean isverified;
    private Boolean isenabled;
    private RoleDTO role;
    private CityDTO city;
}
