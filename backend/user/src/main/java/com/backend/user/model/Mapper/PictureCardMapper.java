package com.backend.user.model.Mapper;

import com.backend.user.model.Entity.PictureCard;
import com.backend.user.model.dto.PictureCardDTO;
import org.springframework.stereotype.Component;

@Component
public class PictureCardMapper {

    // Convert PictureCard entity to PictureCardDTO
    public PictureCardDTO toDto(PictureCard pictureCard) {

        if (pictureCard == null) {
            return null;
        }

        PictureCardDTO pictureCardDTO = new PictureCardDTO();

        pictureCardDTO.setId(pictureCard.getId());
        pictureCardDTO.setPictureurl(pictureCard.getPictureurl());
        pictureCardDTO.setIsvalid(pictureCard.getIsvalid());
        pictureCardDTO.setUserid(pictureCard.getUserid().getId());

        return pictureCardDTO;
    }

    // Convert PictureCardDTO to PictureCard entity
    public PictureCard toEntity(PictureCardDTO pictureCardDTO) {

        if (pictureCardDTO == null) {
            return null;
        }

        PictureCard pictureCard = new PictureCard();

        pictureCard.setId(pictureCardDTO.getId());
        pictureCard.setPictureurl(pictureCardDTO.getPictureurl());
        pictureCard.setIsvalid(pictureCardDTO.getIsvalid());

        return pictureCard;
    }
}