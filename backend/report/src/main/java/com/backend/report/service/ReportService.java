package com.backend.report.service;

import com.backend.report.model.dto.ReportDto;
import com.backend.report.model.entity.AddressDetails;
import com.backend.report.model.entity.CityReport;
import com.backend.report.model.entity.Location;
import com.backend.report.model.entity.Report;
import com.backend.report.model.mapper.ReportMapper;
import com.backend.report.model.repository.CityReportRepository;
import com.backend.report.model.repository.LocationRepository;
import com.backend.report.model.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private CityReportRepository cityReportRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ExternalApiService externalApiService;

    // Create a new Report
    public ReportDto createReport(ReportDto reportDto) {
        // Step 1: Fetch latitude and longitude from the report DTO
        BigDecimal latitude = reportDto.getLocationid().getLatitude();
        BigDecimal longitude = reportDto.getLocationid().getLongitude();

        // Step 2: Use external API to fetch address and city details
        AddressDetails addressDetails = externalApiService.fetchAddressDetails(latitude, longitude);
        if (addressDetails == null) {
            throw new IllegalArgumentException("Unable to fetch address details for the given coordinates");
        }

        // Step 3: Ensure CityReport exists for the fetched city
        String cityName = addressDetails.getCityName();
        String postcode = addressDetails.getPostcode();
        String inseeCode = addressDetails.getInseeCode();

        CityReport cityReport = cityReportRepository.findByCityName(cityName)
                .orElseGet(() -> {
                    CityReport newCityReport = new CityReport();
                    newCityReport.setCityName(cityName);
                    newCityReport.setPostcode(postcode);
                    newCityReport.setInseeCode(inseeCode);
                    return cityReportRepository.save(newCityReport);
                });

        // Step 4: Create and save a new Location
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAddress(addressDetails.getAddress());
        location.setCityReport(cityReport);

        location = locationRepository.save(location);

        // Step 5: Map DTO to entity and assign Location
        Report report = reportMapper.toEntity(reportDto);
        report.setCreationdate(Instant.now());
        report.setLocationid(location);

        // Step 6: Link pictures to the report
        if (report.getPictures() != null) {
            Report finalReport = report;
            report.getPictures().forEach(picture -> picture.setReportid(finalReport));
        }

        // Step 7: Save Report and cascade save Pictures
        report = reportRepository.save(report);

        // Step 8: Return DTO
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

    // Get Reports by User ID
    public List<ReportDto> getReportsByUserId(Integer userid) {
        return reportRepository.findByUserid(userid).stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

}
