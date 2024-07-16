package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.PostForm;
import org.example.lionproj2.service.PostService;
import org.example.lionproj2.util.UserSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WriteController {
    private final PostService postService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping("/vlog.io/write")
    public String writePage(Model model, HttpSession session) {
        model.addAttribute("postForm", new PostForm());
        userSessionUtil.addUserInfoToModel(session, model);

        return "write";
    }

    @PostMapping("/vlog.io/write")
    public String savePost(@ModelAttribute PostForm postForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        String username = userSessionUtil.getUsername(session);

        // 예외 핸들러 해야되는데...
        if (userId == null || username == null) {
            return "redirect:/login";
        }

        Long postId = postService.savePost(userId, postForm);

        return "redirect:/vlog.io/@" + username + "/" + postForm.getTitle();
    }
}

