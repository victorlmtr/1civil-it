package com.backend.report.service;

import com.backend.report.model.dto.ReportTypeDto;
import com.backend.report.model.entity.ReportType;
import com.backend.report.model.mapper.ReportTypeMapper;
import com.backend.report.model.repository.ReportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportTypeService {

    private final ReportTypeRepository reportTypeRepository;
    private final ReportTypeMapper reportTypeMapper;

    // Create or update a report type
    public ReportTypeDto saveOrUpdate(ReportTypeDto reportTypeDto) {
        ReportType reportType = reportTypeMapper.toEntity(reportTypeDto);
        ReportType savedReportType = reportTypeRepository.save(reportType);
        return reportTypeMapper.toDto(savedReportType);
    }

    // Retrieve all report types
    public List<ReportTypeDto> getAllReportTypes() {
        return reportTypeRepository.findAll()
                .stream()
                .map(reportTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Retrieve report type by ID
    public Optional<ReportTypeDto> getReportTypeById(Integer id) {
        return reportTypeRepository.findById(id)
                .map(reportTypeMapper::toDto);
    }

    // Delete report type by ID
    public void deleteReportTypeById(Integer id) {
        reportTypeRepository.deleteById(id);
    }
}
