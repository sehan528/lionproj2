package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.PostUpdateDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.service.PostDetailService;
import org.example.lionproj2.service.PostService;
import org.example.lionproj2.service.RecentPostService;
import org.example.lionproj2.service.UserProfileService;
import org.example.lionproj2.util.UserSessionUtil;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostDetailService postDetailService;
    private final RecentPostService recentPostService;
    private final UserSessionUtil userSessionUtil;
    private final PostService postService;
    private final UserProfileService userService;

    @GetMapping("/vlog.io/@{username}/{postname}")
    public String getPostDetails(@PathVariable String username,
                                 @PathVariable String postname,
                                 @RequestParam(required = false) Long userId,
                                 HttpSession session,
                                 Model model) {
        // 세션에서 userId를 가져옵니다. 세션에 없다면 파라미터로 전달된 userId를 사용합니다.
        Long sessionUserId = (Long) session.getAttribute("userId");
        Long effectiveUserId = sessionUserId != null ? sessionUserId : userId;

        log.info("{} , {}, {}", username, postname, effectiveUserId);

        // Post 디테일 (유저, 글 정보 포함..)
        PostDetailViewDTO postDetail = postDetailService.getPostDetailByUsernameAndPostname(username, postname, effectiveUserId);

        model.addAttribute("post", postDetail);
        model.addAttribute("postContent", postDetail.getContext());
        model.addAttribute("userId", effectiveUserId);

        // 시리즈 정보
        if (postDetail.getSeriesName() != null) {
            List<PostSummaryDTO> seriesPosts = postDetailService.getSeriesPostsBySeriesName(postDetail.getSeriesName());

            model.addAttribute("seriesPosts", seriesPosts);
        }

        // 최근 조회 기록 추가
        if (effectiveUserId != null) {
            recentPostService.addRecentView(effectiveUserId, postDetail.getId());
        }

        userSessionUtil.addUserInfoToModel(session, model);

        return "post-detail";
    }



//    ----------------------------------------------------------------









    @GetMapping("/vlog.io/@{username}/{title}/edit")
    public String editPostForm(@PathVariable String username,
                               @PathVariable String title,
                               Model model,
                               Authentication authentication,
                               HttpSession session) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);
        String authenticatedUserId = authentication.getName();
        log.info("Authenticated user: {}", authenticatedUserId);

        String postAuthorId = userService.getUserIdByUsername(username);
        if (postAuthorId == null || !postAuthorId.equals(authenticatedUserId)) {
            log.warn("Unauthorized access attempt: {} tried to edit {}'s post", authenticatedUserId, username);
            return "error";
        }

        Post post = postService.getPostByUsernameAndTitle(username, decodedTitle);

        if (post == null || !post.getAuthor().getUserId().equals(authenticatedUserId)) {
            log.warn("Post not found: username={}, title={}", username, decodedTitle);
            return "error";
        }

        model.addAttribute("post", post);

        // 사용자 세션 정보 추가
        userSessionUtil.addUserInfoToModel(session, model);

        return "edit-post";
    }

    @PostMapping("/vlog.io/@{username}/{title}/edit")
    public String editPost(@PathVariable String username,
                           @PathVariable String title,
                           @ModelAttribute PostUpdateDTO updatedPost,
                           Authentication authentication,
                           HttpSession session,
                           Model model) {

        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8);
        String authenticatedUserId = authentication.getName();
        log.info("Updating post: username={}, title={}, authenticated userId={}", username, decodedTitle, authenticatedUserId);

        String postAuthorId = userService.getUserIdByUsername(username);
        if (postAuthorId == null || !postAuthorId.equals(authenticatedUserId)) {
            log.warn("Unauthorized update attempt: {} tried to update {}'s post", authenticatedUserId, username);
            return "error";
        }

        boolean success = postService.updatePost(authenticatedUserId, decodedTitle, updatedPost);

        if (success) {
            String encodedNewTitle = URLEncoder.encode(updatedPost.getTitle(), StandardCharsets.UTF_8);
            return "redirect:/vlog.io/@" + username + "/" + encodedNewTitle;
        } else {
            return "error";
        }
    }
}