package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {
    @Id
    @ColumnDefault("nextval('report_reportid_seq'::regclass)")
    @Column(name = "reportid", nullable = false)
    private Integer id;

    @Column(name = "userid", nullable = false)
    private Integer userid;

    @Column(name = "creationdate", nullable = false)
    private Instant creationdate;

    @Column(name = "comment", length = 2000)
    private String comment;

    @Column(name = "addressid")
    private Integer addressid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "typeid", nullable = false)
    private ReportType typeid;

}