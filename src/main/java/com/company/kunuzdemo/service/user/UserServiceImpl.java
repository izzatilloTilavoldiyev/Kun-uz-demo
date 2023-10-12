package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.enums.UserRole;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.company.kunuzdemo.enums.UserStatus.ACTIVE;
import static com.company.kunuzdemo.enums.UserStatus.BLOCKED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO getById(UUID id) {
        User user = findById(id);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with Email: " + email)
        );
    }

    @Override
    public List<UserResponseDTO> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.findAll(true, pageable);
        return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
        }.getType());
    }

    @Override
    public List<UserResponseDTO> filterByRole(int page, int size, String role) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> users = userRepository.filterByRole(UserRole.valueOf(role), pageable).getContent();
        try {
            return modelMapper.map(users, new TypeToken<List<UserResponseDTO>>() {
            }.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + role);
        }

    }

    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("user not found with ID: " + id));
    }

    @Override
    public String blocById(UUID userId) {
        User user = findById(userId);
        user.setStatus(BLOCKED);
        userRepository.save(user);
        return "user blocked";

    }

    @Override
    public String unblockById(UUID userId) {
        User user = findById(userId);
        user.setStatus(ACTIVE);
        userRepository.save(user);
        return "user unblocked";
    }

    @Override
    public UserResponseDTO changeRole(UUID userId, String role) {
        User user = findById(userId);
        user.setRole(UserRole.valueOf(role));

        try {
            return modelMapper.map(userRepository.save(user), UserResponseDTO.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + role);
        }
    }

    @Override
    public String deleteById(UUID userId) {
        User user = findById(userId);
        user.setDeleted(false);
        userRepository.save(user);
        return "user deleted";
    }
}
