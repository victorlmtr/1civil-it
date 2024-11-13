package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reporttype")
public class Reporttype {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reporttype_id_gen")
    @SequenceGenerator(name = "reporttype_id_gen", sequenceName = "reporttype_typeid_seq", allocationSize = 1)
    @Column(name = "typeid", nullable = false)
    private Integer id;

    @Column(name = "typename", nullable = false, length = 100)
    private String typename;
}