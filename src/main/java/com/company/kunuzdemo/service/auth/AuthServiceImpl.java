package com.company.kunuzdemo.service.auth;

import com.company.kunuzdemo.dtos.request.UserCreateDTO;
import com.company.kunuzdemo.dtos.response.AuthResponseDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.entity.VerificationData;
import com.company.kunuzdemo.enums.UserRole;
import com.company.kunuzdemo.enums.UserStatus;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.exception.DuplicateValueException;
import com.company.kunuzdemo.exception.UserPasswordWrongException;
import com.company.kunuzdemo.repository.UserRepository;
import com.company.kunuzdemo.service.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO<UserResponseDTO> create(UserCreateDTO userCreateDTO) {
        checkEmailUnique(userCreateDTO.getEmail());
        checkUserPasswordAndIsValid(userCreateDTO.getPassword(), userCreateDTO.getConfirmPassword());
        User user = modelMapper.map(userCreateDTO, User.class);
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.UNVERIFIED);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setVerificationData(generateVerificationCode());
        User savedUser = userRepository.save(user);
        String message = mailSenderService.sendVerificationCode(user.getEmail(),
                savedUser.getVerificationData().getVerificationCode());
        UserResponseDTO mappedUser = modelMapper.map(savedUser, UserResponseDTO.class);
        return new AuthResponseDTO<>(message, mappedUser);
    }

    @Override
    public String verify(String email, String verificationCode) {
        Optional<User> repoUser = userRepository.findByEmail(email);
        if (repoUser.isEmpty())
            throw new DataNotFoundException("User not found with Email: " + email);
        User user = repoUser.get();
        if (checkVerificationCodeAndExpiration(user.getVerificationData(), verificationCode))
            return "Verification code wrong";
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        return "Successfully verified";
    }

    private void checkEmailUnique(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("User already exists with Email: " + email);
    }

    private void checkUserPasswordAndIsValid(String password, String confirmPassword) {
        if (!Objects.equals(password, confirmPassword))
            throw new UserPasswordWrongException("Password must be same with confirm password: " + confirmPassword);
//        checkPasswordIsValid(password);
    }

    private void checkPasswordIsValid(String password) {
        String passwordRegex = "^ (?=.* [0-9]) (?=.* [a-z]) (?=.* [A-Z]) (?=.* #$%^&-+= ()]) (?=\\S+$). {8, 20}$";
        if (!password.matches(passwordRegex)) {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }

    private VerificationData generateVerificationCode() {
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(100000, 1000000));
        return new VerificationData(verificationCode, LocalDateTime.now());
    }

    public boolean checkVerificationCodeAndExpiration(VerificationData verificationData, String verificationCode) {
        return !verificationData.getVerificationDate().plusMinutes(3).isAfter(LocalDateTime.now())
                || !Objects.equals(verificationData.getVerificationCode(), verificationCode);

    }
}