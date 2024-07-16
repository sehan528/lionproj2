package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.service.SettingService;
import org.example.lionproj2.util.UserSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vlog.io/setting")
public class SettingController {

    private final SettingService settingService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping
    public String settingPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        UserProfileDTO userDTO = settingService.getUserInfo(userId);
        model.addAttribute("user", userDTO);

        userSessionUtil.addUserInfoToModel(session, model);

        return "setting";
    }

    @PostMapping("/update")
    public String updateUserInfo(@ModelAttribute UserProfileDTO userDTO, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingService.updateUserInfo(userId, userDTO);
        return "redirect:/vlog.io/setting";
    }

    @PostMapping("/upload-profile")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingService.uploadProfileImage(userId, file);
        return "redirect:/vlog.io/setting";
    }

    @PostMapping("/remove-profile")
    public String removeProfileImage(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingService.removeProfileImage(userId);
        return "redirect:/vlog.io/setting";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingService.deleteAccount(userId);
        session.invalidate();
        return "redirect:/";
    }
}