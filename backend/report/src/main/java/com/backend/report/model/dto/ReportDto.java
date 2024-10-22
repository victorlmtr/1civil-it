package com.backend.report.model.dto;

import com.backend.report.model.entity.Report;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link Report}
 */
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ReportDto implements Serializable {
    Integer id;
    Integer userid;
    Instant creationdate;
    String comment;
    Integer addressid;
    ReportTypeDto typeid;
}