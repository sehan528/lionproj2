package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.service.RecentPostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RecentPostController {
    private final RecentPostService recentPostService;

    @GetMapping("/vlog.io/read")
    public String recentPostsPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            log.warn("User not logged in, redirecting to login page");
            return "redirect:/login";
        }

        List<RecentPostDTO> recentPosts = recentPostService.getRecentPosts(userId, 0, 20);
        model.addAttribute("recentPosts", recentPosts);
        return "recent-posts";
    }
}