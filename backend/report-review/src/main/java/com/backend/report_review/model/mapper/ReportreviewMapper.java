package com.backend.report_review.model.mapper;

import com.backend.report_review.model.dto.ReportreviewDto;
import com.backend.report_review.model.entity.Reportreview;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReportstatusMapper.class})
public interface ReportreviewMapper {
    Reportreview toEntity(ReportreviewDto reportreviewDto);

    ReportreviewDto toDto(Reportreview reportreview);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reportreview partialUpdate(ReportreviewDto reportreviewDto, @MappingTarget Reportreview reportreview);
}