package com.company.kunuzdemo.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseDTO {

    private String title;
    private String description;
    private Long mediaID;
    private RegionResponseDTO region;
    private CategoryResponseDTO category;
    private String language;
    private LocalDateTime publishedDate;
    private Double viewCount;
    private Double likeCount;
    private Double commentCount;

}