package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.LikedPostDTO;
import org.example.lionproj2.service.LikedPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ListController {
    private final LikedPostService likedPostService;

    @GetMapping("/vlog.io/lists")
    public String likedPostsPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        log.info("Session userId: {}", userId);

        if (userId == null) {
            log.warn("유저 로그인 되지 않음. login page 로 Redirect 함.");
            return "redirect:/login";
        }

        List<LikedPostDTO> likedPosts = likedPostService.getLikedPosts(userId, 0, 20);
        model.addAttribute("likedPosts", likedPosts);
        return "liked-posts";
    }
}

