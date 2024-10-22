package com.backend.report.model.mapper;

import com.backend.report.model.entity.Report;
import com.backend.report.model.dto.ReportDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReportTypeMapper.class})
public interface ReportMapper {
    Report toEntity(ReportDto reportDto);

    ReportDto toDto(Report report);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Report partialUpdate(ReportDto reportDto, @MappingTarget Report report);
}