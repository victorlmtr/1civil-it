package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Value
@Builder
@AllArgsConstructor
public class PictureCardDTO implements Serializable {

    private Integer id;
    private String pictureurl;
    private Boolean isvalid;
    private UserDTO user;
}
