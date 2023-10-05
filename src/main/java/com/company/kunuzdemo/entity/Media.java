package com.company.kunuzdemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "media")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false, unique = true)
    private String fileDownloadUri;

    private boolean deleted;
}
