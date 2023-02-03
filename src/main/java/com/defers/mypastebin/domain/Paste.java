package com.defers.mypastebin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "paste")
public class Paste extends BaseEntity {
    @Id
    private String id;

    @Column(name = "text_description")
    private String textDescription;

    @Column(name = "username")
    private User user;
}
