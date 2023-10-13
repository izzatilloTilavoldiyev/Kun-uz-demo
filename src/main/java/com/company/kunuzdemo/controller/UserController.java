package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<UserResponseDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getAll(page, size));
    }

    @GetMapping("/filter-by-role")
    public ResponseEntity<List<UserResponseDTO>> filterByRole(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam String role
    ) {
        return ResponseEntity.ok(userService.filterByRole(page, size, role));
    }

    @GetMapping("/block/{userId}")
    public ResponseEntity<String> blocById(@PathVariable @NotNull UUID userId) {
       return ResponseEntity.ok(userService.blocById(userId));
    }

    @GetMapping("/unblock/{userId}")
    public ResponseEntity<String> unblockById(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(userService.unblockById(userId));
    }

    @PutMapping("/change-role/{userId}")
    public ResponseEntity<UserResponseDTO> changeRole(
            @PathVariable @NotNull UUID userId,
            @RequestParam @NotBlank String role
    ) {
        return ResponseEntity.ok(userService.changeRole(userId, role));
    }
}
