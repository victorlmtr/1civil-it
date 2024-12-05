package com.backend.report.model.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.backend.report.model.entity.Location}
 */
@Value
public class LocationDto implements Serializable {
    Integer id;
    BigDecimal latitude;
    BigDecimal longitude;
    String address;
    CityReportDto cityReport;
}
