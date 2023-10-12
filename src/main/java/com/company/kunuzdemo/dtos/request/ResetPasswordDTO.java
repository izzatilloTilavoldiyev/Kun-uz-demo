package com.company.kunuzdemo.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDTO {

    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Verification code must not be blank")
    private String verificationCode;

    @NotBlank(message = "New password must not be blank")
    private String newPassword;

    @NotBlank(message = "Confirm password must not be blank")
    private String confirmPassword;
}