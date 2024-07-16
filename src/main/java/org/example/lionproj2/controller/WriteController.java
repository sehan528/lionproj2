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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WriteController {
    private final PostService postService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping("/vlog.io/write")
    public String writePage(Model model, HttpSession session) {
        userSessionUtil.addUserInfoToModel(session, model);
        model.addAttribute("postForm", new PostForm());

        Long userId = (Long) session.getAttribute("user_id");
        if (userId != null) {
            List<String> userSeries = postService.getUserSeries(userId);
            model.addAttribute("user_series", userSeries);
        }


        return "write";
    }

    @PostMapping("/vlog.io/write")
    public String savePost(@ModelAttribute PostForm postForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        // 예외 핸들러 해야되는데...
        if (userId == null) {
            return "redirect:/login";
        }

        Long postId = postService.savePost(userId, postForm);
        String username = userSessionUtil.getUsername(session);
        if(username == null) {
            return "redirect:/login";
        }
        // 작성 완료 후 임시 저장 글 (LocalStorage) 삭제.
        return "redirect:/vlog.io/@" + username + "/" + postForm.getTitle() + "?clearTemp=true";
    }
}

