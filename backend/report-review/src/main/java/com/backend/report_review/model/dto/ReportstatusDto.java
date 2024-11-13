package com.backend.report_review.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.backend.report_review.model.entity.Reportstatus}
 */
@Value
public class ReportstatusDto implements Serializable {
    Integer id;
    String statusname;
}