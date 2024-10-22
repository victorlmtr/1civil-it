package com.backend.user.model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('role_roleid_seq'")
    @Column(name = "roleid", nullable = false)
    private Integer id;

    @Column(name = "rolename", nullable = false, length = 50)
    private String rolename;

    @OneToMany(mappedBy = "roleid")
    private Set<User> users = new LinkedHashSet<>();

}