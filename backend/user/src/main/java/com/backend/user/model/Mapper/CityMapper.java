package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.City;
import com.backend.user.model.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO toDTO(City city);

    City toEntity(CityDTO cityDTO);
}
