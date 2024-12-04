package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.User;
import com.backend.user.model.dto.UserDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMapper.class, CityMapper.class})
public interface UserMapper {

    @Mapping(source = "passwordhash", target = "password")
    UserDTO toDTO(User user);

    @Mapping(source = "password", target = "passwordhash")
    User toEntity(UserDTO userDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDTO userDTO, @MappingTarget User user);
}
