package com.backend.report_review.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "reportreview")
public class Reportreview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportreview_id_gen")
    @SequenceGenerator(name = "reportreview_id_gen", sequenceName = "reportreview_reportreviewid_seq", allocationSize = 1)
    @Column(name = "reportreviewid", nullable = false)
    private Integer id;

    @Column(name = "reportid", nullable = false)
    private Integer reportid;

    @Column(name = "enforcerid", nullable = false)
    private Integer enforcerid;

    @Column(name = "reviewcomment", length = 2000)
    private String reviewcomment;

    @Column(name = "reviewtimestamp", nullable = false)
    private Instant reviewtimestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "statusid", nullable = false)
    private Reportstatus statusid;

}