package org.example.lionproj2.controller;

import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.service.RecentService;
import org.example.lionproj2.service.TrendingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TrendingService trendingService;
    private final RecentService recentService;

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
    public String mainPage(@RequestParam(defaultValue = "week") String period, Model model) {
        List<TrendingPostDTO> trendingPosts = trendingService.getTrendingPosts(period, 0, 20);
        model.addAttribute("trendingPosts", trendingPosts);
        model.addAttribute("period", period);
        return "main";
    }

    @GetMapping("/vlog.io/recent")
    public String recentPage(Model model) {
        List<RecentPostDTO> recentPosts = recentService.getRecentPosts(0, 20);
        model.addAttribute("recentPosts", recentPosts);
        return "recent";
    }
}