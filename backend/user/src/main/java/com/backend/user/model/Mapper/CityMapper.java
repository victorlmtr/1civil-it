package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.City;
import com.backend.user.model.dto.CityDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityMapper {

    CityDTO toDTO(City city);

    City toEntity(CityDTO cityDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City partialUpdate(CityDTO cityDTO, @MappingTarget City city);
}
