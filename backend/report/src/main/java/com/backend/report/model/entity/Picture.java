package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "picture_id_gen")
    @SequenceGenerator(name = "picture_id_gen", sequenceName = "picture_pictureid_seq", allocationSize = 1)
    @Column(name = "pictureid", nullable = false)
    private Integer id;

    @Column(name = "pictureurl", nullable = false, length = 100)
    private String pictureurl;

    @Column(name = "data", length = Integer.MAX_VALUE)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reportid", nullable = false)
    private Report reportid;

}