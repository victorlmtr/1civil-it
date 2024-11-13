package com.backend.report.model.mapper;

import com.backend.report.model.dto.ReportDto;
import com.backend.report.model.entity.Report;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ReporttypeMapper.class, LocationMapper.class, PictureMapper.class})
public interface ReportMapper {
    Report toEntity(ReportDto reportDto);

    @AfterMapping
    default void linkPictures(@MappingTarget Report report) {
        report.getPictures().forEach(picture -> picture.setReportid(report));
    }

    ReportDto toDto(Report report);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Report partialUpdate(ReportDto reportDto, @MappingTarget Report report);
}