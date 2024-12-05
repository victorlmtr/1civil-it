package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_gen")
    @SequenceGenerator(name = "report_id_gen", sequenceName = "report_reportid_seq", allocationSize = 1)
    @Column(name = "reportid", nullable = false)
    private Integer id;

    @Column(name = "userid", nullable = false)
    private Integer userid;

    @Column(name = "creationdate")
    private Instant creationdate;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "typeid", nullable = false)
    private Reporttype typeid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationid", nullable = false)
    private Location locationid;

    @OneToMany(mappedBy = "reportid", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Picture> pictures = new LinkedHashSet<>();
}
