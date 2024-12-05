package com.backend.report.model.mapper;

import com.backend.report.model.dto.ReporttypeDto;
import com.backend.report.model.entity.Reporttype;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReporttypeMapper {
    Reporttype toEntity(ReporttypeDto reporttypeDto);

    ReporttypeDto toDto(Reporttype reporttype);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reporttype partialUpdate(ReporttypeDto reporttypeDto, @MappingTarget Reporttype reporttype);
}
