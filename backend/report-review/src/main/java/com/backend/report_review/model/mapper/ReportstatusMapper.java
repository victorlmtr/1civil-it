package com.backend.report_review.model.mapper;

import com.backend.report_review.model.dto.ReportstatusDto;
import com.backend.report_review.model.entity.Reportstatus;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportstatusMapper {
    Reportstatus toEntity(ReportstatusDto reportstatusDto);

    ReportstatusDto toDto(Reportstatus reportstatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reportstatus partialUpdate(ReportstatusDto reportstatusDto, @MappingTarget Reportstatus reportstatus);
}