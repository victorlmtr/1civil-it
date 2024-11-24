package com.backend.user.service;

import com.backend.user.model.Entity.Role;
import com.backend.user.model.Mapper.RoleMapper;
import com.backend.user.model.dto.RoleDTO;
import com.backend.user.model.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    // Create a new Role
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    // Get all Roles
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get a Role by ID
    public Optional<RoleDTO> getRoleById(Integer id) {
        return roleRepository.findById(id).map(roleMapper::toDTO);
    }

    // Update an existing Role
    public Optional<RoleDTO> updateRole(Integer id, RoleDTO roleDTO) {
        return roleRepository.findById(id).map(existingRole -> {
            roleMapper.partialUpdate(roleDTO, existingRole);
            Role updatedRole = roleRepository.save(existingRole);
            return roleMapper.toDTO(updatedRole);
        });
    }

    // Delete a Role by ID
    public boolean deleteRole(Integer id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
