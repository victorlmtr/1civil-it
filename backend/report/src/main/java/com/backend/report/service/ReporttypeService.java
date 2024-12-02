package com.backend.report.service;

import com.backend.report.model.dto.ReporttypeDto;
import com.backend.report.model.entity.Reporttype;
import com.backend.report.model.mapper.ReporttypeMapper;
import com.backend.report.model.repository.ReporttypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReporttypeService {

    @Autowired
    private ReporttypeRepository reporttypeRepository;

    @Autowired
    private ReporttypeMapper reporttypeMapper;

    // Create a new Reporttype
    public ReporttypeDto createReporttype(ReporttypeDto reporttypeDto) {
        Reporttype reporttype = reporttypeMapper.toEntity(reporttypeDto);
        reporttype = reporttypeRepository.save(reporttype);
        return reporttypeMapper.toDto(reporttype);
    }

    // Get all Reporttypes
    public List<ReporttypeDto> getAllReporttypes() {
        List<Reporttype> reporttypes = reporttypeRepository.findAll();
        return reporttypes.stream()
                .map(reporttype -> {
                    return reporttypeMapper.toDto(reporttype);
                })
                .collect(Collectors.toList());
    }


    // Get a Reporttype by ID
    public Optional<ReporttypeDto> getReporttypeById(Integer id) {
        return reporttypeRepository.findById(id).map(reporttypeMapper::toDto);
    }

    // Update an existing Reporttype
    public Optional<ReporttypeDto> updateReporttype(Integer id, ReporttypeDto reporttypeDto) {
        return reporttypeRepository.findById(id).map(existingReporttype -> {
            reporttypeMapper.partialUpdate(reporttypeDto, existingReporttype);
            Reporttype updatedReporttype = reporttypeRepository.save(existingReporttype);
            return reporttypeMapper.toDto(updatedReporttype);
        });
    }

    // Delete a Reporttype by ID
    public boolean deleteReporttype(Integer id) {
        if (reporttypeRepository.existsById(id)) {
            reporttypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

