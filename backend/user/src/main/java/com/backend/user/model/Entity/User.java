package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @ColumnDefault("nextval('users_userid_seq'")
    @Column(name = "userid", nullable = false)
    private Integer id;

    @Column(name = "firstname", nullable = false, length = 100)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Column(name = "passwordhash", nullable = false, length = 225)
    private String passwordhash;

    @Column(name = "creationdate", nullable = false)
    private Instant creationdate;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "phonenumber", nullable = false, length = 10)
    private String phonenumber;

    @Column(name = "isverified", nullable = false)
    private Boolean isverified = false;

    @Column(name = "isenabled", nullable = false)
    private Boolean isenabled = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    private Role roleid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityid")
    private City cityid;

}