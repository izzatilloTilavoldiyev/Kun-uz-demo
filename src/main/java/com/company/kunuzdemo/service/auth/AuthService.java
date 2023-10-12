package com.company.kunuzdemo.service.auth;

import com.company.kunuzdemo.dtos.request.LoginDTO;
import com.company.kunuzdemo.dtos.request.UserCreateDTO;
import com.company.kunuzdemo.dtos.response.AuthResponseDTO;
import com.company.kunuzdemo.dtos.response.TokenDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;

public interface AuthService {

    AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO);

    String verify(String email, String verificationCode);

    String newVerifyCode(String email);

    TokenDTO login(LoginDTO loginDTO);

    String forgotPassword(String email);

}
