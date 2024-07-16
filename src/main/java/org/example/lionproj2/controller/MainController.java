package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.service.RecentService;
import org.example.lionproj2.service.TrendingService;
import org.example.lionproj2.service.UserProfileService;
import org.example.lionproj2.util.UserSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final TrendingService trendingService;
    private final RecentService recentService;
    private final UserProfileService userService;
    private final UserSessionUtil userSessionUtil;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);


    @GetMapping("/")
    public RedirectView redirectToMainPage() {
        return new RedirectView("/vlog.io");
    }

    // [임시] 전역적으로 이 문제를 해결할 수 있는 로직을 찾고 적용하시오!
    @GetMapping("/vlog.io/")
    public RedirectView redirectToMainPage2() {
        return new RedirectView("/vlog.io");
    }


    @GetMapping("/vlog.io")
    public String mainPage(@RequestParam(defaultValue = "week") String period, Model model, HttpSession session) {
        List<TrendingPostDTO> trendingPosts = trendingService.getTrendingPosts(period, 0, 20);

//        log.debug("Trending posts for period {}: {}", period, trendingPosts);
        logger.info("Fetched {} trending posts for period: {}", trendingPosts.size(), period);

        model.addAttribute("trendingPosts", trendingPosts);
        model.addAttribute("period", period);

        userSessionUtil.addUserInfoToModel(session, model);

        return "main";
    }


    @GetMapping("/vlog.io/recent")
    public String recentPage(Model model, HttpSession session) {
        List<RecentPostDTO> recentPosts = recentService.getRecentPosts(0, 20);

        model.addAttribute("recentPosts", recentPosts);

        userSessionUtil.addUserInfoToModel(session, model);
        return "recent";
    }
}