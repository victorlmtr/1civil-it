package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Value
public class PictureCardDTO implements Serializable {

    private Integer id;
    private String pictureurl;
    private Boolean isvalid;
    private UserDTO user;
}
