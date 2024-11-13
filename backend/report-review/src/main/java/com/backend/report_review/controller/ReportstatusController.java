package com.backend.report_review.controller;

import com.backend.report_review.model.dto.ReportstatusDto;
import com.backend.report_review.service.ReportstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews/statuses")
public class ReportstatusController {

    private final ReportstatusService reportstatusService;

    @Autowired
    public ReportstatusController(ReportstatusService reportstatusService) {
        this.reportstatusService = reportstatusService;
    }

    // Create a new Reportstatus
    @PostMapping
    public ResponseEntity<ReportstatusDto> createReportstatus(@RequestBody ReportstatusDto reportstatusDto) {
        ReportstatusDto createdStatus = reportstatusService.createReportstatus(reportstatusDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    // Get all Reportstatuses
    @GetMapping
    public ResponseEntity<List<ReportstatusDto>> getAllReportstatuses() {
        List<ReportstatusDto> statuses = reportstatusService.getAllReportstatuses();
        return ResponseEntity.ok(statuses);
    }

    // Get a Reportstatus by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportstatusDto> getReportstatusById(@PathVariable Integer id) {
        Optional<ReportstatusDto> reportstatus = reportstatusService.getReportstatusById(id);
        return reportstatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Reportstatus
    @PutMapping("/{id}")
    public ResponseEntity<ReportstatusDto> updateReportstatus(@PathVariable Integer id, @RequestBody ReportstatusDto reportstatusDto) {
        Optional<ReportstatusDto> updatedStatus = reportstatusService.updateReportstatus(id, reportstatusDto);
        return updatedStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Reportstatus by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportstatus(@PathVariable Integer id) {
        boolean deleted = reportstatusService.deleteReportstatus(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
