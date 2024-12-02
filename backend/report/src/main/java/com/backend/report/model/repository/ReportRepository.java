package com.backend.report.model.repository;

import com.backend.report.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findByUserid(Integer userid);
}
