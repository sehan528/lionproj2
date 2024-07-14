package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.LoginDTO;
import org.example.lionproj2.dto.SignupDTO;
import org.example.lionproj2.entity.Role;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.exception.DuplicateUserException;
import org.example.lionproj2.exception.InvalidCredentialsException;
import org.example.lionproj2.repository.RoleRepository;
import org.example.lionproj2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupDTO signupDto) {
        if (userRepository.existsByUserId(signupDto.getUserId())) {
            throw new DuplicateUserException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new DuplicateUserException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .userId(signupDto.getUserId())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .profileImg("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png")
                .isBan(false)
                .registrationDate(LocalDateTime.now())
                .build();

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.save(user);
    }

    public User login(LoginDTO loginDto) {
        User user = userRepository.findByUserId(loginDto.getUserId())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return user;
    }
}