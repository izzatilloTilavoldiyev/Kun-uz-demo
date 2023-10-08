package com.company.kunuzdemo.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryUpdateDTO {
    private String nameUZ;
    private String nameRU;
    private String nameEN;
    private boolean visible;
}
