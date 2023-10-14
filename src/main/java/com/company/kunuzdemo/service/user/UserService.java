package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.company.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO getById(UUID id);

    UserResponseDTO getByEmail(String email);

    List<UserResponseDTO> getAll(int page, int size);

    List<UserResponseDTO> filterByRole(int page, int size, String role);

    UserResponseDTO changeRole(ChangeRoleDTO roleDTO);

    UserResponseDTO updateProfile(UUID userId, UserUpdateProfileDTO dto);

    User getUserByEmail(String email);

    String blockById(UUID userId);

    String unblockById(UUID userId);

    String deleteById(UUID userId);

    void deleteSelectedUsers(List<UUID> userIds);
}
