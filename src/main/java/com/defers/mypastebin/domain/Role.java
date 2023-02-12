package com.defers.mypastebin.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
