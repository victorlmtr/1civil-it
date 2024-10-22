package com.backend.user.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDTO {

    private Integer id;
    private String rolename;
    private Set<Integer> userIds;
}