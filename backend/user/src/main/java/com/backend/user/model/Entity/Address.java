package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @ColumnDefault("nextval('address_address_id_seq'")
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "streetnumber", nullable = false, length = 5)
    private String streetnumber;

    @Column(name = "streetname", nullable = false, length = 100)
    private String streetname;

    @Column(name = "adressdetails", nullable = false, length = 225)
    private String adressdetails;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cityid", nullable = false)
    private City city;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

}