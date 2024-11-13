package com.backend.report_review.service;

import com.backend.report_review.model.dto.ReportreviewDto;
import com.backend.report_review.model.dto.ReportstatusDto;
import com.backend.report_review.model.entity.Reportreview;
import com.backend.report_review.model.entity.Reportstatus;
import com.backend.report_review.model.repository.ReportreviewRepository;
import com.backend.report_review.model.repository.ReportstatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportreviewService {

    @Autowired
    private ReportreviewRepository reportreviewRepository;

    @Autowired
    private ReportstatusRepository reportstatusRepository;

    // Create a new Reportreview
    public ReportreviewDto createReportreview(ReportreviewDto reportreviewDto) {
        Reportreview reportreview = new Reportreview();
        reportreview.setReportid(reportreviewDto.getReportid());
        reportreview.setEnforcerid(reportreviewDto.getEnforcerid());
        reportreview.setReviewcomment(reportreviewDto.getReviewcomment());
        reportreview.setReviewtimestamp(Instant.now());

        Reportstatus status = reportstatusRepository.findById(reportreviewDto.getStatusid().getId())
                .orElseThrow(() -> new RuntimeException("Status not found"));
        reportreview.setStatusid(status);

        reportreview = reportreviewRepository.save(reportreview);

        return new ReportreviewDto(
                reportreview.getId(),
                reportreview.getReportid(),
                reportreview.getEnforcerid(),
                reportreview.getReviewcomment(),
                reportreview.getReviewtimestamp(),
                new ReportstatusDto(status.getId(), status.getStatusname())
        );
    }

    // Get all Reportreviews
    public List<ReportreviewDto> getAllReportreviews() {
        return reportreviewRepository.findAll().stream()
                .map(review -> new ReportreviewDto(
                        review.getId(),
                        review.getReportid(),
                        review.getEnforcerid(),
                        review.getReviewcomment(),
                        review.getReviewtimestamp(),
                        new ReportstatusDto(review.getStatusid().getId(), review.getStatusid().getStatusname())
                ))
                .collect(Collectors.toList());
    }

    // Get a Reportreview by ID
    public Optional<ReportreviewDto> getReportreviewById(Integer id) {
        return reportreviewRepository.findById(id).map(review -> new ReportreviewDto(
                review.getId(),
                review.getReportid(),
                review.getEnforcerid(),
                review.getReviewcomment(),
                review.getReviewtimestamp(),
                new ReportstatusDto(review.getStatusid().getId(), review.getStatusid().getStatusname())
        ));
    }

    // Update an existing Reportreview
    public Optional<ReportreviewDto> updateReportreview(Integer id, ReportreviewDto reportreviewDto) {
        return reportreviewRepository.findById(id).map(existingReview -> {
            existingReview.setReportid(reportreviewDto.getReportid());
            existingReview.setEnforcerid(reportreviewDto.getEnforcerid());
            existingReview.setReviewcomment(reportreviewDto.getReviewcomment());

            Reportstatus status = reportstatusRepository.findById(reportreviewDto.getStatusid().getId())
                    .orElseThrow(() -> new RuntimeException("Status not found"));
            existingReview.setStatusid(status);

            Reportreview updatedReview = reportreviewRepository.save(existingReview);

            return new ReportreviewDto(
                    updatedReview.getId(),
                    updatedReview.getReportid(),
                    updatedReview.getEnforcerid(),
                    updatedReview.getReviewcomment(),
                    updatedReview.getReviewtimestamp(),
                    new ReportstatusDto(status.getId(), status.getStatusname())
            );
        });
    }

    // Delete a Reportreview by ID
    public boolean deleteReportreview(Integer id) {
        if (reportreviewRepository.existsById(id)) {
            reportreviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
