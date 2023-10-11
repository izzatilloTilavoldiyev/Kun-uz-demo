package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    UserResponseDTO getById(UUID id);

    User getUserByEmail(String email);

    Page<UserResponseDTO> getAll(int page, int size);

    Page<UserResponseDTO> filterByRole(int page, int size, String role);
}
