package com.defers.mypastebin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "paste")
public class Paste extends BaseEntity {
    @Id
    private String id;

    @Column(name = "text_description")
    @NotBlank(message = "Text description must not be blank")
    @NotNull(message = "Text description must not be null")
    private String textDescription;

    @Column(name = "username")
    private User user;
}
