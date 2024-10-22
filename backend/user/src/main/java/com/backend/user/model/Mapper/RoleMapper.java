package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.Role;
import com.backend.user.model.dto.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    // Convert Role entity to RoleDTO
    public RoleDTO toDto(Role role) {

        if (role == null) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId(role.getId());
        roleDTO.setRolename(role.getRolename());
        roleDTO.setUserIds(role.getUsers().stream()
                .map(user -> user.getId()) // Assuming User has a getId method
                .collect(Collectors.toSet()));

        return roleDTO;
    }

    // Convert RoleDTO to Role entity
    public Role toEntity(RoleDTO roleDTO) {

        if (roleDTO == null) {
            return null;
        }

        Role role = new Role();

        role.setId(roleDTO.getId());
        role.setRolename(roleDTO.getRolename());

        return role;
    }
}