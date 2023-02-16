package com.defers.mypastebin.domain;

import com.defers.mypastebin.enums.Roles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user_role")
public class UserRole_OLD extends BaseEntity{
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private String username;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "role")
    private Roles role;
}
