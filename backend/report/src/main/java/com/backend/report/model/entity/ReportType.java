package com.backend.report.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "reporttype")
public class ReportType {
    @Id
    @ColumnDefault("nextval('reporttype_typeid_seq'::regclass)")
    @Column(name = "typeid", nullable = false)
    private Integer id;

    @Column(name = "typename", nullable = false, length = 50)
    private String typename;

}