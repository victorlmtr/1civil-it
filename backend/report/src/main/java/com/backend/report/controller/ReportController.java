package com.backend.report.controller;

import com.backend.report.model.dto.ReportDto;
import com.backend.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Create a new Report
    @PostMapping
    public ResponseEntity<ReportDto> createReport(@RequestBody ReportDto reportDto) {
        ReportDto createdReport = reportService.createReport(reportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    // Get all Reports
    @GetMapping
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    

    // Get a Report by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Integer id) {
        Optional<ReportDto> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Report
    @PutMapping("/{id}")
    public ResponseEntity<ReportDto> updateReport(@PathVariable Integer id, @RequestBody ReportDto reportDto) {
        Optional<ReportDto> updatedReport = reportService.updateReport(id, reportDto);
        return updatedReport.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Report by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer id) {
        boolean deleted = reportService.deleteReport(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Get Reports by User ID
    @GetMapping("/user/{userid}")
    public ResponseEntity<List<ReportDto>> getReportsByUserId(@PathVariable Integer userid) {
        List<ReportDto> reports = reportService.getReportsByUserId(userid);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

}
