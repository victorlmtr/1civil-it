package com.backend.user.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Boolean isverified;
    private Boolean isenabled;
    private Instant creationdate;
    private Integer addressid;
    private Integer roleid;
    private Set<Integer> picturecardIds;
}