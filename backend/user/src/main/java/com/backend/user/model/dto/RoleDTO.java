package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Value
public class RoleDTO implements Serializable {

    private Integer id;
    private String rolename;
}
