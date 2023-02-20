package com.defers.mypastebin.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
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

    @JoinColumn(name = "username")
    @ManyToOne(cascade = {CascadeType.ALL})
    private User user;
}
