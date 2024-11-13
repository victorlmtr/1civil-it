package com.backend.report_review.model.repository;

import com.backend.report_review.model.entity.Reportreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportreviewRepository extends JpaRepository<Reportreview, Integer> {
}

