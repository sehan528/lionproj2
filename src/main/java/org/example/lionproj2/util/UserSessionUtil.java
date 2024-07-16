package org.example.lionproj2.util;

import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.service.UserProfileService;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Component
public class UserSessionUtil {

    private final UserProfileService userService;

    public UserSessionUtil(UserProfileService userService) {
        this.userService = userService;
    }

    public void addUserInfoToModel(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            String username = userService.getUsernameById(userId);
            String profileImg = userService.getUserProfileImgById(userId);

            model.addAttribute("username", username);
            model.addAttribute("profileImg", profileImg);
            model.addAttribute("isLoggedIn", true);

        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }

    public String getUsername(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            log.info("user 를 찾았습니다! {}", userId);
            return userService.getUsernameById(userId);
        }
        log.warn("user 를 찾을 수 없습니다!");
        return null;
    }

    public String getUserProfileImg(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            log.info("user 프로필 이미지를 찾음! userId : {}", userId);
            return userService.getUserProfileImgById(userId);
        }
        log.warn("user 프로필 이미지를 찾을 수 없음.");
        return null;
    }

}