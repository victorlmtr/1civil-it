package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Integer id;
    private String rolename;
}
