package com.backend.report.model.repository;

import com.backend.report.model.entity.CityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityReportRepository extends JpaRepository<CityReport, Integer> {
    Optional<CityReport> findByCityName(String cityName);
}
