package com.backend.report.model.repository;

import com.backend.report.model.entity.Reporttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporttypeRepository extends JpaRepository<Reporttype, Integer> {
}

