package com.backend.report_review.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reportstatus")
public class Reportstatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportstatus_id_gen")
    @SequenceGenerator(name = "reportstatus_id_gen", sequenceName = "reportstatus_statusid_seq", allocationSize = 1)
    @Column(name = "statusid", nullable = false)
    private Integer id;

    @Column(name = "statusname", nullable = false, length = 40)
    private String statusname;

    @OneToMany(mappedBy = "statusid")
    private Set<Reportreview> reportreviews = new LinkedHashSet<>();

}