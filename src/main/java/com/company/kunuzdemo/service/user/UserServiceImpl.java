package com.company.kunuzdemo.service.user;

import com.company.kunuzdemo.entity.User;
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

import java.util.UUID;

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
    public Page<UserResponseDTO> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        Pageable pageable = PageRequest.of(page, size, sort);
        return modelMapper.map(userRepository.findAll(pageable),
                new TypeToken<Page<UserResponseDTO>>() {}.getType());
    }

    @Override
    public Page<UserResponseDTO> filterByRole(int page, int size, String role) {
        return null;
    }

    private User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("user not found with ID: " + id));
    }
}
