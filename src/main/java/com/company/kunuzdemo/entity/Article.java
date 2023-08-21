package com.company.kunuzdemo.entity;

import com.company.kunuzdemo.enums.ArticleStatus;
import com.company.kunuzdemo.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Article extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true, columnDefinition = "text")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Attachment attachment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArticleType articleType;

    @Column(updatable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @Enumerated(EnumType.STRING)
    private Language language;

    private LocalDateTime publishedDate;

    private Double viewCount = 0D;

}
