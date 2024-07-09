package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.domain.Role;
import org.example.lionproj2.domain.User;
import org.example.lionproj2.model.LoginRequestDTO;
import org.example.lionproj2.model.UserDTO;
import org.example.lionproj2.repository.RoleRepository;
import org.example.lionproj2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new IllegalStateException("ROLE_USER does not exist");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword()) // 실제로는 암호화 필요
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .registrationDate(new Timestamp(System.currentTimeMillis()))
                .build();

        user.addRole(userRole);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User authenticate(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.getUsername();
        String password = loginRequestDTO.getPassword();

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public User matchUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}
