package com.backend.report.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.backend.report.model.entity.Report}
 */
@Value
public class ReportDto implements Serializable {
    Integer id;
    Integer userid;
    Instant creationdate;
    String comment;
    ReporttypeDto typeid;
    LocationDto locationid;
    Set<PictureDto> pictures;
}
