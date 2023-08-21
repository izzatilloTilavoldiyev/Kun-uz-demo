package com.company.kunuzdemo.entity;

import com.company.kunuzdemo.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Like extends BaseEntity{

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Article article;
}
