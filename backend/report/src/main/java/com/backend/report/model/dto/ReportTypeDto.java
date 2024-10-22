package com.backend.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.backend.report.model.entity.ReportType}
 */
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ReportTypeDto implements Serializable {
    Integer id;
    String typename;
}