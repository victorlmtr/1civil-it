package com.backend.report.model.mapper;

import com.backend.report.model.dto.PictureDto;
import com.backend.report.model.entity.Picture;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReportMapper.class})
public interface PictureMapper {
    Picture toEntity(PictureDto pictureDto);

    PictureDto toDto(Picture picture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Picture partialUpdate(PictureDto pictureDto, @MappingTarget Picture picture);
}