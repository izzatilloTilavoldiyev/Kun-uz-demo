package com.company.kunuzdemo.dtos.request;

import com.company.kunuzdemo.entity.Media;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateProfileDTO {

    private String firstName;
    private String lastName;
    private Long mediaId;

}
