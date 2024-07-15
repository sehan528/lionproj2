package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.LikedPostDTO;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.repository.UserRepository;
import org.example.lionproj2.service.LikedPostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ListController {
    private final LikedPostService likedPostService;
    private final UserRepository userRepository;

    @GetMapping("/vlog.io/lists")
    public String likedPostsPage(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not logged in, redirecting to login page");
            return "redirect:/login";
        }

        String userId = authentication.getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        Long userIdLong = user.getId();

        log.info("User ID from authentication: {}", userIdLong);

        List<LikedPostDTO> likedPosts = likedPostService.getLikedPosts(userIdLong, 0, 20);
        model.addAttribute("likedPosts", likedPosts);
        return "liked-posts";
    }
}