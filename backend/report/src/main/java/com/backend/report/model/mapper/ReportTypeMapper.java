package com.backend.report.model.mapper;

import com.backend.report.model.dto.ReportTypeDto;
import com.backend.report.model.entity.ReportType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportTypeMapper {
    ReportType toEntity(ReportTypeDto reportTypeDto);

    ReportTypeDto toDto(ReportType reportType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ReportType partialUpdate(ReportTypeDto reportTypeDto, @MappingTarget ReportType reportType);
}