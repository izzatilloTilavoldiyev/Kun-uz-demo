package com.company.kunuzdemo.dtos.request;

import com.company.kunuzdemo.entity.Media;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateProfileDTO {

    private String firstName;
    private String lastName;
    private Media media;

}
