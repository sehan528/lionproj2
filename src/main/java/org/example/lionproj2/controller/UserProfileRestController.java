package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.AboutMeDTO;
import org.example.lionproj2.service.UserProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserProfileRestController {

    private final UserProfileService userProfileService;

    @PostMapping("/about")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateAboutMe(@RequestBody AboutMeDTO aboutMeDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            log.warn("User not logged in, redirecting to login page");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }


        log.info("어바웃미 수정시도: {}", userId);
        log.info("내용은 {}",aboutMeDTO.toString());
        userProfileService.updateAboutMe(userId, aboutMeDTO.getAboutMe());

        return ResponseEntity.ok("About Me updated successfully");
    }
}