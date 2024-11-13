package com.backend.report_review.controller;

import com.backend.report_review.model.dto.ReportreviewDto;
import com.backend.report_review.service.ReportreviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews/reviews")
public class ReportreviewController {

    private final ReportreviewService reportreviewService;

    @Autowired
    public ReportreviewController(ReportreviewService reportreviewService) {
        this.reportreviewService = reportreviewService;
    }

    // Create a new Reportreview
    @PostMapping
    public ResponseEntity<ReportreviewDto> createReportreview(@RequestBody ReportreviewDto reportreviewDto) {
        ReportreviewDto createdReview = reportreviewService.createReportreview(reportreviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    // Get all Reportreviews
    @GetMapping
    public ResponseEntity<List<ReportreviewDto>> getAllReportreviews() {
        List<ReportreviewDto> reviews = reportreviewService.getAllReportreviews();
        return ResponseEntity.ok(reviews);
    }

    // Get a Reportreview by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportreviewDto> getReportreviewById(@PathVariable Integer id) {
        Optional<ReportreviewDto> reportreview = reportreviewService.getReportreviewById(id);
        return reportreview.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Reportreview
    @PutMapping("/{id}")
    public ResponseEntity<ReportreviewDto> updateReportreview(@PathVariable Integer id, @RequestBody ReportreviewDto reportreviewDto) {
        Optional<ReportreviewDto> updatedReview = reportreviewService.updateReportreview(id, reportreviewDto);
        return updatedReview.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a Reportreview by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportreview(@PathVariable Integer id) {
        boolean deleted = reportreviewService.deleteReportreview(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
