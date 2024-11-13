package com.backend.user.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @ColumnDefault("nextval('role_roleid_seq'")
    @Column(name = "roleid", nullable = false)
    private Integer id;

    @Column(name = "rolename", nullable = false, length = 100)
    private String rolename;

}