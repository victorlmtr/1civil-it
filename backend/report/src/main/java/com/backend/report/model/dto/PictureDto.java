package com.backend.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.backend.report.model.entity.Picture}
 */
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PictureDto implements Serializable {
    Integer id;
    String pictureurl;
    String data;
    ReportDto reportid;
}