package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.request.ChangeRoleDTO;
import com.company.kunuzdemo.dtos.request.PasswordUpdateDTO;
import com.company.kunuzdemo.dtos.request.UserUpdateProfileDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.Media;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.enums.UserRole;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.exception.UserPasswordWrongException;
import com.company.kunuzdemo.repository.MediaRepository;
import com.company.kunuzdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.company.kunuzdemo.enums.UserStatus.ACTIVE;
import static com.company.kunuzdemo.enums.UserStatus.BLOCKED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MediaRepository mediaRepository;

    @Override
    public User getUserByID(UUID userID) {
        return findById(userID);
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        User user = findById(id);
        return modelMapper.map(user, UserResponseDTO.class);
    }




    @Override
    public List<UserResponseDTO> searchByEmail(String email, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "email");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.searchByEmail(email, pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
    }


    @Override
    public List<UserResponseDTO> getAll(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.findAllUsers(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public List<UserResponseDTO> filterByRole(Integer page, Integer size, String role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            List<User> users = userRepository.filterByRole(UserRole.valueOf(role), pageable).getContent();
            return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + role);
        }
    }

    @Override
    public List<UserResponseDTO> getAllDeleted(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.findAllDeleted(pageable).getContent();
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public UserResponseDTO changeRole(ChangeRoleDTO roleDTO) {
        User user = findById(roleDTO.getUserId());

        try {
            user.setRole(UserRole.valueOf(roleDTO.getRole()));
            return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + roleDTO.getRole());
        }
    }

    @Override
    public UserResponseDTO updateProfile(UUID userId, UserUpdateProfileDTO dto) {
        User user = findById(userId);

        if (dto.getMediaId() != null) {
            Media media = mediaRepository.findById(dto.getMediaId()).orElseThrow(
                    () -> new DataNotFoundException("Media not found with ID: " + dto.getMediaId()));
            user.setMedia(media);
        }

        modelMapper.map(dto, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with Email: " + email)
        );
    }

    private User findById(UUID id) {
        return userRepository.getUserById(id).orElseThrow(
                () -> new DataNotFoundException("user not found with ID: " + id));
    }

    @Override
    public String blockById(UUID userId) {
        User user = findById(userId);
        if(user.getStatus().equals(BLOCKED)) {
            return "User already blocked";
        }
        user.setStatus(BLOCKED);
        userRepository.save(user);
        return "User blocked";
    }

    @Override
    public String unblockById(UUID userId) {
        User user = findById(userId);
        if(user.getStatus().equals(ACTIVE)) {
            return "User already unblocked";
        }
        user.setStatus(ACTIVE);
        userRepository.save(user);
        return "User unblocked";
    }


    @Override
    public String deleteById(UUID userId) {
        User user = findById(userId);
        user.setDeleted(true);
        userRepository.save(user);
        return "user deleted";
    }

    @Override
    public void deleteSelectedUsers(List<UUID> userIds) {
        for (UUID userId : userIds) {
            deleteById(userId);
        }
    }
}
