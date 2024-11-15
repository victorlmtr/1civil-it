package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "picturecard")
public class PictureCard {
    @Id
    @ColumnDefault("nextval('picturecard_picturecardid_seq'")
    @Column(name = "picturecardid", nullable = false)
    private Integer id;

    @Column(name = "pictureurl", nullable = false, length = 100)
    private String pictureurl;

    @Column(name = "isvalid", nullable = false)
    private Boolean isvalid = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

}