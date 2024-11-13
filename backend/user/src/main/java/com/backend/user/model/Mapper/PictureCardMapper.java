package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.PictureCard;
import com.backend.user.model.dto.PictureCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PictureCardMapper {
    PictureCardMapper INSTANCE = Mappers.getMapper(PictureCardMapper.class);

    PictureCardDTO toDTO(PictureCard picturecard);

    PictureCard toEntity(PictureCardDTO picturecardDTO);
}
