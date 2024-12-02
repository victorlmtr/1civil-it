package com.backend.report.service;

import com.backend.report.model.dto.CityReportDto;
import com.backend.report.model.entity.CityReport;
import com.backend.report.model.mapper.CityReportMapper;
import com.backend.report.model.repository.CityReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityReportService {

    @Autowired
    private CityReportRepository cityReportRepository;

    @Autowired
    private CityReportMapper cityReportMapper;

    // Create a new CityReport
    public CityReportDto createCityReport(CityReportDto cityReportDto) {
        CityReport cityReport = cityReportMapper.toEntity(cityReportDto);
        cityReport = cityReportRepository.save(cityReport);
        return cityReportMapper.toDto(cityReport);
    }

    // Get all CityReports
    public List<CityReportDto> getAllCityReports() {
        return cityReportRepository.findAll().stream()
                .map(cityReportMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get a CityReport by ID
    public Optional<CityReportDto> getCityReportById(Integer id) {
        return cityReportRepository.findById(id).map(cityReportMapper::toDto);
    }

    // Update an existing CityReport
    public Optional<CityReportDto> updateCityReport(Integer id, CityReportDto cityReportDto) {
        return cityReportRepository.findById(id).map(existingCityReport -> {
            cityReportMapper.partialUpdate(cityReportDto, existingCityReport);
            CityReport updatedCityReport = cityReportRepository.save(existingCityReport);
            return cityReportMapper.toDto(updatedCityReport);
        });
    }

    // Delete a CityReport by ID
    public boolean deleteCityReport(Integer id) {
        if (cityReportRepository.existsById(id)) {
            cityReportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

