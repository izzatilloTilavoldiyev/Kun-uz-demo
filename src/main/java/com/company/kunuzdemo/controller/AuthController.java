package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.*;
import com.company.kunuzdemo.dtos.response.AuthResponseDTO;
import com.company.kunuzdemo.dtos.response.TokenDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.service.auth.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDTO<UserResponseDTO>> signUp(
            @Valid @RequestBody UserCreateDTO userCreateDTO
    ) {
        AuthResponseDTO<UserResponseDTO> responseDTO = authService.create(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verify(
            @Valid @RequestBody VerifyDTO verifyDTO
    ) {
        String response = authService.verify(verifyDTO.getEmail(), verifyDTO.getVerificationCode());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/new-verification-code")
    public ResponseEntity<String> newVerificationCode(
            @RequestParam String email
    ) {
        String response = authService.newVerifyCode(email);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/sing-in")
    public ResponseEntity<TokenDTO> signIn(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        TokenDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam @Email String email
    ) {
        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    ) {
        String response = authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO
    ) {
        return ResponseEntity.ok(authService.updatePassword(passwordUpdateDTO));
    }

}