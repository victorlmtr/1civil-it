package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.User;
import com.backend.user.model.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Convert User entity to UserDTO
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhonenumber(user.getPhonenumber());
        userDTO.setIsverified(user.getIsverified());
        userDTO.setIsenabled(user.getIsenabled());
        userDTO.setCreationdate(user.getCreationdate());
        userDTO.setAddressid(user.getAddressid());
        userDTO.setRoleid(user.getRoleid().getId());
        userDTO.setPicturecardIds(user.getPictureCards().stream()
                .map(picturecard -> picturecard.getId())
                .collect(Collectors.toSet()));

        return userDTO;
    }

    // Convert UserDTO to User entity
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        user.setId(userDTO.getId());
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPhonenumber(userDTO.getPhonenumber());
        user.setIsverified(userDTO.getIsverified());
        user.setIsenabled(userDTO.getIsenabled());
        user.setCreationdate(userDTO.getCreationdate());
        user.setAddressid(userDTO.getAddressid());

        return user;
    }
}