package com.company.kunuzdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Region extends BaseEntity{

    @Column(name = "name_uz",nullable = false, unique = true)
    private String nameUZ;

    @Column(name = "name_uz",nullable = false, unique = true)
    private String nameRU;

    @Column(name = "name_uz",nullable = false, unique = true)
    private String nameEN;

    private boolean visible = true;
}