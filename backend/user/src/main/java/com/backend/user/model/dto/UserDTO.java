package com.backend.user.model.dto;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Instant creationdate;
    private Boolean isverified;
    private Boolean isenabled;
    private RoleDTO role;
    private CityDTO city;
}
