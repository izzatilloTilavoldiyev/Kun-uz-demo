package com.company.kunuzdemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateDTO {

    @NotBlank(message = "Name UZ must not be blank")
    private String nameUZ;

    @NotBlank(message = "Name RU must not be blank")
    private String nameRU;

    @NotBlank(message = "Name EN must not be blank")
    private String nameEN;
}
