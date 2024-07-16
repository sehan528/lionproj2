package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.SearchResultDTO;
import org.example.lionproj2.service.SearchService;
import org.example.lionproj2.util.UserSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping("/vlog.io/search")
    public String search(@RequestParam(required = false) String q, Model model, HttpSession session) {
        if (q != null && !q.isEmpty()) {
            SearchResultDTO result = searchService.search(q.toLowerCase(), 0, 20);
            model.addAttribute("searchQuery", q);
            model.addAttribute("searchResults", result.getPosts());
            model.addAttribute("totalResults", result.getTotalCount());
        }

        userSessionUtil.addUserInfoToModel(session, model);

        return "search";
    }
}

