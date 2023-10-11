package com.company.kunuzdemo.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyDTO {

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Verification code must not be blank")
    private String verificationCode;

}