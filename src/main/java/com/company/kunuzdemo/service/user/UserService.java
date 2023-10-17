package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.company.kunuzdemo.dtos.request.PasswordUpdateDTO;
import com.company.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserByID(UUID userID);

    UserResponseDTO getById(UUID id);

    User getUserByEmail(String email);

    List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size);

    List<UserResponseDTO> getAll(Integer page, Integer size);

    List<UserResponseDTO> filterByRole(Integer page, Integer size, String role);

    List<UserResponseDTO> getAllDeleted(Integer page, Integer size);

    String blockByID(UUID userId);

    String unblockByID(UUID userId);

    UserResponseDTO changeRole(ChangeRoleDTO roleDTO);

    UserResponseDTO updateProfile(UUID userId, UserUpdateProfileDTO dto);

    String deleteByID(UUID userId);

    void deleteSelectedUsers(List<UUID> userIds);
}
