package com.backend.report.model.dto;

import com.backend.report.model.entity.CityReport;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link CityReport}
 */
@Value
public class CityReportDto implements Serializable {
    Integer id;
    String cityName;
    String postcode;
    String inseeCode;
}