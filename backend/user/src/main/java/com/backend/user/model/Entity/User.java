package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('users_userid_seq'")
    @Column(name = "userid", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 70)
    private String lastname;

    @Column(name = "passwordhash", nullable = false)
    private String passwordhash;

    @Column(name = "creationdate", nullable = false)
    private Instant creationdate;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "phonenumber", nullable = false, length = 15)
    private String phonenumber;

    @Column(name = "isverified", nullable = false)
    private Boolean isverified = false;

    @Column(name = "isenabled", nullable = false)
    private Boolean isenabled = false;

    @Column(name = "addressid")
    private Integer addressid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    private Role roleid;

    @OneToMany(mappedBy = "userid")
    private Set<PictureCard> pictureCards = new LinkedHashSet<>();

}