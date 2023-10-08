package com.company.kunuzdemo.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionResponseDTO {
    private String nameUZ;
    private String nameRU;
    private String nameEN;
}
