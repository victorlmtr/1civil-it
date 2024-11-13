package com.backend.report_review.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.backend.report_review.model.entity.Reportreview}
 */
@Value
public class ReportreviewDto implements Serializable {
    Integer id;
    Integer reportid;
    Integer enforcerid;
    String reviewcomment;
    Instant reviewtimestamp;
    ReportstatusDto statusid;
}