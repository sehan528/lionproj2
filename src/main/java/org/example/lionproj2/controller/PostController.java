package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.service.PostDetailService;
import org.example.lionproj2.service.RecentPostService;
import org.example.lionproj2.util.UserSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostDetailService postDetailService;
    private final RecentPostService recentPostService;
    private final UserSessionUtil userSessionUtil;

    @GetMapping("/vlog.io/@{username}/{postname}")
    public String getPostDetails(@PathVariable String username,
                                 @PathVariable String postname,
                                 @RequestParam(required = false) Long userId,
                                 HttpSession session,
                                 Model model) {
        // 세션에서 userId를 가져옵니다. 세션에 없다면 파라미터로 전달된 userId를 사용합니다.
        Long sessionUserId = (Long) session.getAttribute("userId");
        Long effectiveUserId = sessionUserId != null ? sessionUserId : userId;

        PostDetailViewDTO postDetail = postDetailService.getPostDetailByUsernameAndPostname(username, postname, effectiveUserId);

        model.addAttribute("post", postDetail);
        model.addAttribute("userId", effectiveUserId);

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
}