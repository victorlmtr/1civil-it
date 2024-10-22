package com.backend.report.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @ColumnDefault("nextval('picture_pictureid_seq'::regclass)")
    @Column(name = "pictureid", nullable = false)
    private Integer id;

    @Column(name = "pictureurl", nullable = false, length = 100)
    private String pictureurl;

    @Column(name = "data", length = 2000)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reportid", nullable = false)
    private Report reportid;

}