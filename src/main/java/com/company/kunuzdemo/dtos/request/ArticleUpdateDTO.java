package com.company.kunuzdemo.dtos.request;

import com.company.kunuzdemo.enums.Language;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleUpdateDTO {
    private String title;
    private String description;
    private Long mediaID;
    private UUID reginID;
    private UUID categoryID;
    private String language;
}
