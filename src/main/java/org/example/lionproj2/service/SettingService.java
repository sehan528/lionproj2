package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.exception.UserNotFoundException;
import org.example.lionproj2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/image/profile/";
    private static final String DEFAULT_PROFILE_IMG = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";

    public UserProfileDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return convertToDTO(user);
    }

    @Transactional
    public void updateUserInfo(Long userId, UserProfileDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }

    @Transactional
    public void uploadProfileImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        String fileName = userId + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        try {
            Files.write(path, file.getBytes());
//            user.setProfileImg(UPLOAD_DIR + fileName);
            user.setProfileImg("/image/profile/" + fileName);

            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Transactional
    public void removeProfileImage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setProfileImg(DEFAULT_PROFILE_IMG);
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(Long userId) {
        userRepository.deleteById(userId);
    }

    private UserProfileDTO convertToDTO(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfileImg(user.getProfileImg());
        return dto;
    }
}