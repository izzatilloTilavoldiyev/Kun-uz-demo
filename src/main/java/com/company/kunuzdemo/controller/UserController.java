package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.company.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/search-by-email")
    public ResponseEntity<List<UserResponseDTO>> searchByEmail(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam @Email String email
    ) {
        return ResponseEntity.ok(userService.searchByEmail(email, page, size));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userService.getAll(page, size));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/filter-by-role")
    public ResponseEntity<List<UserResponseDTO>> filterByRole(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam String role
    ) {
        return ResponseEntity.ok(userService.filterByRole(page, size, role));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @GetMapping("/all-deleted")
    public ResponseEntity<List<UserResponseDTO>> getAllDeleted(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(userService.getAllDeleted(page, size));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/block/{userID}")
    public ResponseEntity<String> blocByID(@PathVariable UUID userID) {
        return ResponseEntity.ok(userService.blockByID(userID));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/unblock/{userID}")
    public ResponseEntity<String> unblockByID(@PathVariable UUID userID) {
        return ResponseEntity.ok(userService.unblockByID(userID));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/change-role")
    public ResponseEntity<UserResponseDTO> changeRole(
            @RequestBody @Valid ChangeRoleDTO role
    ) {
        return ResponseEntity.ok(userService.changeRole(role));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PutMapping("/update-profile/{userID}")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @PathVariable UUID userID,
            @RequestBody @Valid UserUpdateProfileDTO dto
    ) {
        return ResponseEntity.ok(userService.updateProfile(userID, dto));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{userID}")
    ResponseEntity<String> deleteByID(@PathVariable UUID userID) {
        return ResponseEntity.ok(userService.deleteByID(userID));
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")

    @DeleteMapping("/delete-selected")
    public ResponseEntity<String> deleteSelected(@Valid @RequestParam List<UUID> userIDs) {
        userService.deleteSelectedUsers(userIDs);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
