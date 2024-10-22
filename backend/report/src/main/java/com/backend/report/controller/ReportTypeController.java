package com.backend.report.controller;

import com.backend.report.model.dto.ReportTypeDto;
import com.backend.report.service.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/report-types")
@RequiredArgsConstructor
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    // Créer ou mettre à jour un type de rapport
    @PostMapping
    public ResponseEntity<ReportTypeDto> saveOrUpdateReportType(@RequestBody ReportTypeDto reportTypeDto) {
        ReportTypeDto savedReportType = reportTypeService.saveOrUpdate(reportTypeDto);
        return ResponseEntity.ok(savedReportType);
    }

    // Récupérer tous les types de rapport
    @GetMapping
    public ResponseEntity<List<ReportTypeDto>> getAllReportTypes() {
        List<ReportTypeDto> reportTypes = reportTypeService.getAllReportTypes();
        return ResponseEntity.ok(reportTypes);
    }

    // Récupérer un type de rapport selon son ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportTypeDto> getReportTypeById(@PathVariable Integer id) {
        Optional<ReportTypeDto> reportTypeDto = reportTypeService.getReportTypeById(id);
        return reportTypeDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un type de rapport selon son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportTypeById(@PathVariable Integer id) {
        reportTypeService.deleteReportTypeById(id);
        return ResponseEntity.noContent().build();
    }
}

