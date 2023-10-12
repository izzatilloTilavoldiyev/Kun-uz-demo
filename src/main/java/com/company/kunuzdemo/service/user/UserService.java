package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO getById(UUID id);

    User getUserByEmail(String email);

    List<UserResponseDTO> getAll(int page, int size);

    List<UserResponseDTO> filterByRole(int page, int size, String role);

    String blocById(UUID userId);

    String unblockById(UUID userId);

    UserResponseDTO changeRole(UUID userId, String role);
}
