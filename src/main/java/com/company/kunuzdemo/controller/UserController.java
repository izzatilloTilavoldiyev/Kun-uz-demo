package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.PasswordUpdateDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAll(page, size));
    }

    @GetMapping("/filter-by-role")
    public ResponseEntity<Page<UserResponseDTO>> filterByRole(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam String role
    ) {
        return ResponseEntity.ok(userService.filterByRole(page, size, role));
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @Valid @RequestBody PasswordUpdateDTO passwordUpdateDTO
    ) {
        String response = userService.updatePassword(passwordUpdateDTO);
        return ResponseEntity.ok(response);
    }

}
