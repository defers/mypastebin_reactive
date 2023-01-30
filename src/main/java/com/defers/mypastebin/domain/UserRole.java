package com.defers.mypastebin.domain;

import com.defers.mypastebin.enums.Roles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity{
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "user_name")
    private String userName;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "role")
    private Roles role;
}
