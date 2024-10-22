package com.backend.user.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PictureCardDTO {

    private Integer id;
    private String pictureurl;
    private Boolean isvalid;
    private Integer userid;
}