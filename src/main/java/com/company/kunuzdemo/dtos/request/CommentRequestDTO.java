package com.company.kunuzdemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO {
    @NotBlank(message = "test must not be blank")
    private String text;
    @NotNull(message = "User ID must not be null")
    private UUID userID;
    @NotNull(message = "Article ID must not be null")
    private UUID articleID;

}
