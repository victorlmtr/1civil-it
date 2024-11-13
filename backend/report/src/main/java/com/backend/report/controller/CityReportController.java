package com.backend.report.controller;

import com.backend.report.model.dto.CityReportDto;
import com.backend.report.service.CityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports/cities")
public class CityReportController {

    private final CityReportService cityReportService;

    @Autowired
    public CityReportController(CityReportService cityReportService) {
        this.cityReportService = cityReportService;
    }

    // Create a new CityReport
    @PostMapping
    public ResponseEntity<CityReportDto> createCityReport(@RequestBody CityReportDto cityReportDto) {
        CityReportDto createdCityReport = cityReportService.createCityReport(cityReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCityReport);
    }

    // Get all CityReports
    @GetMapping
    public ResponseEntity<List<CityReportDto>> getAllCityReports() {
        List<CityReportDto> cityReports = cityReportService.getAllCityReports();
        return ResponseEntity.ok(cityReports);
    }

    // Get a CityReport by ID
    @GetMapping("/{id}")
    public ResponseEntity<CityReportDto> getCityReportById(@PathVariable Integer id) {
        Optional<CityReportDto> cityReport = cityReportService.getCityReportById(id);
        return cityReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing CityReport
    @PutMapping("/{id}")
    public ResponseEntity<CityReportDto> updateCityReport(@PathVariable Integer id, @RequestBody CityReportDto cityReportDto) {
        Optional<CityReportDto> updatedCityReport = cityReportService.updateCityReport(id, cityReportDto);
        return updatedCityReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a CityReport by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCityReport(@PathVariable Integer id) {
        boolean deleted = cityReportService.deleteCityReport(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
