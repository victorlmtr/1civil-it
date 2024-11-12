package com.backend.report.model.repository;

import com.backend.report.model.entity.CityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityReportRepository extends JpaRepository<CityReport, Integer> {
}
