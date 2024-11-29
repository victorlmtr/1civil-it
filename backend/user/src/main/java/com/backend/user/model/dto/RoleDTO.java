package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Value
@Builder
@AllArgsConstructor
public class RoleDTO implements Serializable {

    private Integer id;
    private String rolename;
}
