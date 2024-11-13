package com.backend.report.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.backend.report.model.entity.Picture}
 */
@Value
public class PictureDto implements Serializable {
    Integer id;
    String pictureurl;
    String data;
}