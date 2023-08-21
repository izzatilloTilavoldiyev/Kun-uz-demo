package com.company.kunuzdemo.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Attachment extends BaseEntity{

    private String originalName;

    private String extension;

    private String type;

    private Long size;

    private String path;
}
