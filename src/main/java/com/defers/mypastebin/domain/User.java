package com.defers.mypastebin.domain;

import com.defers.mypastebin.enums.Roles;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    private String username;
    private String password;

    @Email
    @NotNull
    private String email;

    @Enumerated(value = EnumType.STRING)
    @OneToOne(mappedBy = "username")
    private Roles role;
}
