package com.backend.user.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureCardDTO {

    private Integer id;
    private String pictureurl;
    private Boolean isvalid;
    private UserDTO user;
}
