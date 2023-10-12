package com.company.kunuzdemo.dtos.response;

import com.company.kunuzdemo.entity.Media;
import com.company.kunuzdemo.enums.UserRole;
import com.company.kunuzdemo.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private String firstName;

    private String lastName;

    private String email;

    @JsonIgnore
    private String password;

    private UserRole role;

    private UserStatus status;

    private Long mediaId;
}