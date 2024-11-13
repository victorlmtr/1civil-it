package com.backend.user.model.dto;

import lombok.Data;

@Data
public class PictureCardDTO {

    private Integer id;
    private String pictureurl;
    private Boolean isvalid;
    private Integer userId;
}
