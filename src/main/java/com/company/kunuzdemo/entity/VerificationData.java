package com.company.kunuzdemo.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationData {

    private String verificationCode;

    private LocalDateTime verificationDate;

}