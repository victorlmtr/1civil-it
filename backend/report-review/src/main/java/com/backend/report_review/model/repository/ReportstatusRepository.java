package com.backend.report_review.model.repository;

import com.backend.report_review.model.entity.Reportstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportstatusRepository extends JpaRepository<Reportstatus, Integer> {
}
