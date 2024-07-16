package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.service.UserProfileService;
import org.example.lionproj2.util.UserSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vlog.io")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping("/@{username}")
    public String redirectToUserPosts(@PathVariable String username) {
        return "redirect:/vlog.io/@" + username + "/posts";
    }

    @GetMapping("/@{username}/posts")
    public String getUserPosts(@PathVariable String username, Model model, HttpSession session) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(username);
        log.info("UserProfile: {}", userProfile);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentPage", "posts");

        userSessionUtil.addUserInfoToModel(session, model);

        return "userProfile";
    }

    @GetMapping("/@{username}/series")
    public String getUserSeries(@PathVariable String username, Model model, HttpSession session) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(username);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentPage", "series");

        userSessionUtil.addUserInfoToModel(session, model);

        return "userProfile";
    }

    @GetMapping("/@{username}/about")
    public String getUserAbout(@PathVariable String username, Model model, HttpSession session) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(username);
        String aboutMe = userProfileService.getUserAboutMe(username);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("aboutMe", aboutMe);
        model.addAttribute("currentPage", "about");

        userSessionUtil.addUserInfoToModel(session, model);

        return "userProfile";
    }

}