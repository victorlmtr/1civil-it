package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.Address;
import com.backend.user.model.dto.AddressDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface AddressMapper {

    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressDTO addressDTO, @MappingTarget Address address);
}
