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
public class PasswordUpdateDTO {

    @NotNull(message = "User ID must not be null")
    private UUID userID;

    @NotBlank(message = "Old password must not be blank")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    private String newPassword;

    @NotBlank(message = "Repeat password must not be blank")
    private String repeatPassword;

}