package com.backend.report.model.mapper;

import com.backend.report.model.entity.CityReport;
import com.backend.report.model.dto.CityReportDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CityReportMapper {
    CityReport toEntity(CityReportDto cityReportDto);

    CityReportDto toDto(CityReport cityReport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CityReport partialUpdate(CityReportDto cityReportDto, @MappingTarget CityReport cityReport);
}