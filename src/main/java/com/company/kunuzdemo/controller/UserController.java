package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.company.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
    public ResponseEntity<UserResponseDTO> getById(@PathVariable @NotNull UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/search-by-email")
    public ResponseEntity<UserResponseDTO> searchByEmail(@RequestParam @Email String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
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

    @PutMapping("/block/{userId}")
    public ResponseEntity<String> blocById(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(userService.blocById(userId));
    }

    @PutMapping("/unblock/{userId}")
    public ResponseEntity<String> unblockById(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(userService.unblockById(userId));
    }

    @PutMapping("/change-role")
    public ResponseEntity<UserResponseDTO> changeRole(
            @RequestBody @Valid ChangeRoleDTO role
    ) {
        return ResponseEntity.ok(userService.changeRole(role));
    }

    @PutMapping("/update-profile/{userId}")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @PathVariable @NotNull UUID userId,
            @RequestBody @Valid UserUpdateProfileDTO dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(userId, dto));
    }

    @DeleteMapping("/delete/{userId}")
    ResponseEntity<String> deleteById(@PathVariable @NotNull UUID userId) {
        return ResponseEntity.ok(userService.deleteById(userId));
    }

    @DeleteMapping("/delete-selected")
    public ResponseEntity<String> deleteSelected(@Valid @RequestParam List<UUID> userIds) {
        userService.deleteSelectedUsers(userIds);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
