package com.backend.report.controller;

import com.backend.report.model.dto.ReporttypeDto;
import com.backend.report.service.ReporttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports/types")
public class ReporttypeController {

    private final ReporttypeService reporttypeService;

    @Autowired
    public ReporttypeController(ReporttypeService reporttypeService) {
        this.reporttypeService = reporttypeService;
    }

    // Create a new Reporttype
    @PostMapping
    public ResponseEntity<ReporttypeDto> createReporttype(@RequestBody ReporttypeDto reporttypeDto) {
        ReporttypeDto createdReporttype = reporttypeService.createReporttype(reporttypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReporttype);
    }

    // Get all Reporttypes
    @GetMapping
    public ResponseEntity<List<ReporttypeDto>> getAllReporttypes() {
        List<ReporttypeDto> reporttypes = reporttypeService.getAllReporttypes();
        return ResponseEntity.ok(reporttypes);
    }

    // Get a Reporttype by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReporttypeDto> getReporttypeById(@PathVariable Integer id) {
        Optional<ReporttypeDto> reporttype = reporttypeService.getReporttypeById(id);
        return reporttype.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Reporttype
    @PutMapping("/{id}")
    public ResponseEntity<ReporttypeDto> updateReporttype(@PathVariable Integer id, @RequestBody ReporttypeDto reporttypeDto) {
        Optional<ReporttypeDto> updatedReporttype = reporttypeService.updateReporttype(id, reporttypeDto);
        return updatedReporttype.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Reporttype by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporttype(@PathVariable Integer id) {
        boolean deleted = reporttypeService.deleteReporttype(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
