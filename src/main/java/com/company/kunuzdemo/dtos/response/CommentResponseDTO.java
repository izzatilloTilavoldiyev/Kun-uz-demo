package com.company.kunuzdemo.dtos.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDTO {

    private String text;
    private UUID userID;
    private UUID articleID;

}
