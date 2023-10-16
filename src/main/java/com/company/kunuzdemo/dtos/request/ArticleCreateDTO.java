package com.company.kunuzdemo.dtos.request;

import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.entity.Region;
import com.company.kunuzdemo.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleCreateDTO {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "UserID must not be null")
    private UUID userID;

    private UUID mediaID;

    private Region region;

    @NotBlank(message = "Category must not be blank")
    private Category category;

    @NotBlank(message = "Language must not be blank")
    private Language language;
}