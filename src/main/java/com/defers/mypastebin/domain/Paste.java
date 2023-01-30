package com.defers.mypastebin.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "paste")
public class Paste extends BaseEntity {
    @Id
    @GeneratedValue(generator = "paste_generator")
    @GenericGenerator(name = "paste_generator", strategy = "com.defers.mypastebin.domain.utils.PasteIdGenerator")
    private String id;

    @Column(name = "text_description")
    private String textDescription;
}
