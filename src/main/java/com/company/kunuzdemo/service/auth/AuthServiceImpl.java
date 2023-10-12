package com.company.kunuzdemo.service.auth;

import com.company.kunuzdemo.config.jwt.JwtService;
import com.company.kunuzdemo.dtos.request.LoginDTO;
import com.company.kunuzdemo.dtos.request.UserCreateDTO;
import com.company.kunuzdemo.dtos.response.AuthResponseDTO;
import com.company.kunuzdemo.dtos.response.TokenDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.entity.VerificationData;
import com.company.kunuzdemo.enums.UserRole;
import com.company.kunuzdemo.enums.UserStatus;
import com.company.kunuzdemo.exception.DuplicateValueException;
import com.company.kunuzdemo.exception.UserPasswordWrongException;
import com.company.kunuzdemo.repository.BadRequestException;
import com.company.kunuzdemo.repository.UserRepository;
import com.company.kunuzdemo.service.mail.MailSenderService;
import com.company.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO) {
        checkEmailUnique(userCreateDTO.getEmail());
        checkUserPasswordAndIsValid(userCreateDTO.getPassword(), userCreateDTO.getConfirmPassword());
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setVerificationData(generateVerificationData());
        User savedUser = userRepository.save(user);
        String message = mailSenderService.sendVerificationCode(user.getEmail(),
                savedUser.getVerificationData().getVerificationCode());
        UserResponseDTO mappedUser = modelMapper.map(savedUser, UserResponseDTO.class);
        return new AuthResponseDTO<>(message, mappedUser);
    }

    @Override
    public String verify(String email, String verificationCode) {
        User user = userService.getUserByEmail(email);
        if (checkVerificationCodeAndExpiration(user.getVerificationData(), verificationCode))
            return "Verification code wrong";
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return "Successfully verified";
    }

    @Override
    public String newVerifyCode(String email) {
        User user = userService.getUserByEmail(email);
        user.setVerificationData(generateVerificationData());
        userRepository.save(user);
        return mailSenderService.sendVerificationCode(email, user.getVerificationData().getVerificationCode());
    }

    //todo: check delete user
    @Override
    public TokenDTO login(LoginDTO loginDTO) {
        User user = userService.getUserByEmail(loginDTO.getEmail());
        if (user.getStatus().equals(UserStatus.UNVERIFIED))
            throw new BadRequestException("User unverified");
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new UserPasswordWrongException("User password wrong with Password: " + loginDTO.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );
        return jwtService.generateToken(user.getEmail());
    }

    @Override
    public String forgotPassword(String email) {
        User user = userService.getUserByEmail(email);
        user.setVerificationData(generateVerificationData());
        userRepository.save(user);
        return mailSenderService.sendVerificationCode(email, user.getVerificationData().getVerificationCode());
    }

    private void checkEmailUnique(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("User already exists with Email: " + email);
    }

    private void checkUserPasswordAndIsValid(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword))
            throw new UserPasswordWrongException("Password must be same with confirm password: " + confirmPassword);
        checkPasswordIsValid(password);
    }

    private void checkPasswordIsValid(String password) {
        String passwordRegex = "^ (?=.* [0-9]) (?=.* [a-z]) (?=.* [A-Z]) (?=.* #$%^&-+= ()]) (?=\\S+$). {8,20}$";
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }

    private VerificationData generateVerificationData() {
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(100000, 1000000));
        return new VerificationData(verificationCode, LocalDateTime.now());
    }

    public boolean checkVerificationCodeAndExpiration(VerificationData verificationData, String verificationCode) {
        return !verificationData.getVerificationDate().plusMinutes(3).isAfter(LocalDateTime.now())
                || !Objects.equals(verificationData.getVerificationCode(), verificationCode);

    }
}