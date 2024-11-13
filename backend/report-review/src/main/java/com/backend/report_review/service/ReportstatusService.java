package com.backend.report_review.service;

import com.backend.report_review.model.dto.ReportstatusDto;
import com.backend.report_review.model.entity.Reportstatus;
import com.backend.report_review.model.repository.ReportstatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportstatusService {

    @Autowired
    private ReportstatusRepository reportstatusRepository;

    // Create a new Reportstatus
    public ReportstatusDto createReportstatus(ReportstatusDto reportstatusDto) {
        Reportstatus reportstatus = new Reportstatus();
        reportstatus.setStatusname(reportstatusDto.getStatusname());
        reportstatus = reportstatusRepository.save(reportstatus);
        return new ReportstatusDto(reportstatus.getId(), reportstatus.getStatusname());
    }

    // Get all Reportstatuses
    public List<ReportstatusDto> getAllReportstatuses() {
        return reportstatusRepository.findAll()
                .stream()
                .map(rs -> new ReportstatusDto(rs.getId(), rs.getStatusname()))
                .collect(Collectors.toList());
    }

    // Get a Reportstatus by ID
    public Optional<ReportstatusDto> getReportstatusById(Integer id) {
        return reportstatusRepository.findById(id)
                .map(rs -> new ReportstatusDto(rs.getId(), rs.getStatusname()));
    }

    // Update an existing Reportstatus
    public Optional<ReportstatusDto> updateReportstatus(Integer id, ReportstatusDto reportstatusDto) {
        return reportstatusRepository.findById(id).map(reportstatus -> {
            reportstatus.setStatusname(reportstatusDto.getStatusname());
            Reportstatus updatedStatus = reportstatusRepository.save(reportstatus);
            return new ReportstatusDto(updatedStatus.getId(), updatedStatus.getStatusname());
        });
    }

    // Delete a Reportstatus by ID
    public boolean deleteReportstatus(Integer id) {
        if (reportstatusRepository.existsById(id)) {
            reportstatusRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
