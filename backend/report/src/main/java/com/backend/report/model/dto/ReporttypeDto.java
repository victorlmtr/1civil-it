package com.backend.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.backend.report.model.entity.Reporttype}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporttypeDto implements Serializable {
    Integer id;
    String typename;
}
