package com.backend.report.service;

import com.backend.report.model.dto.ReportDto;
import com.backend.report.model.entity.Report;
import com.backend.report.model.mapper.ReportMapper;
import com.backend.report.model.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    // Create a new Report
    public ReportDto createReport(ReportDto reportDto) {
        Report report = reportMapper.toEntity(reportDto);
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    // Get all Reports
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a Report by ID
    public Optional<ReportDto> getReportById(Integer id) {
        return reportRepository.findById(id).map(reportMapper::toDto);
    }

    // Update an existing Report
    public Optional<ReportDto> updateReport(Integer id, ReportDto reportDto) {
        return reportRepository.findById(id).map(existingReport -> {
            reportMapper.partialUpdate(reportDto, existingReport);
            Report updatedReport = reportRepository.save(existingReport);
            return reportMapper.toDto(updatedReport);
        });
    }

    // Delete a Report by ID
    public boolean deleteReport(Integer id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
