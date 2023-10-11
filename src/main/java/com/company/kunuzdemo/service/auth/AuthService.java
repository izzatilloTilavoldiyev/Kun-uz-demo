package com.company.kunuzdemo.service.auth;

import com.company.kunuzdemo.dtos.request.UserCreateDTO;
import com.company.kunuzdemo.dtos.response.AuthResponseDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;

public interface AuthService {

    AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO);
}
