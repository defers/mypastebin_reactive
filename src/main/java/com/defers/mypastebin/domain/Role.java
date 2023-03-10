package com.defers.mypastebin.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "role")
public class Role extends BaseEntity{
    @Id
    @SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
