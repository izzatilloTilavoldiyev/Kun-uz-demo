package com.company.kunuzdemo.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @Email
    @NotBlank(message = "Email must not be blank")
    @Size(min = 12, max = 40, message = "Email length must be between 12 and 40 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "Confirm password must not be blank")
    private String confirmPassword;
}