package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.PictureCard;
import com.backend.user.model.dto.PictureCardDTO;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface PictureCardMapper {

    PictureCardDTO toDTO(PictureCard picturecard);

    PictureCard toEntity(PictureCardDTO picturecardDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PictureCard partialUpdate(PictureCardDTO pictureCardDTO, @MappingTarget PictureCard pictureCard);
}
