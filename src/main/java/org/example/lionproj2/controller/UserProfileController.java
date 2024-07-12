package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.service.UserProfileService;
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

    @GetMapping("/@{username}")
    public String redirectToUserPosts(@PathVariable String username) {
        return "redirect:/vlog.io/@" + username + "/posts";
    }

    @GetMapping("/@{username}/posts")
    public String getUserPosts(@PathVariable String username, Model model) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(username);
        log.info("UserProfile: {}", userProfile);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentPage", "posts");
        return "userProfile";
    }

    @GetMapping("/@{username}/series")
    public String getUserSeries(@PathVariable String username, Model model) {
        UserProfileDTO userProfile = userProfileService.getUserProfile(username);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("currentPage", "series");
        return "userProfile";
    }
}