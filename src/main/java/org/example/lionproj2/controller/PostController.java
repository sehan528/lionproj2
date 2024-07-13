package org.example.lionproj2.controller;

import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.service.PostDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostDetailService postDetailService;

    @GetMapping("/vlog.io/@{username}/{postname}")
    public String getPostDetails(@PathVariable String username,
                                    @PathVariable String postname,
                                    @RequestParam(required = false) Long userId,
                                    Model model) {
        PostDetailViewDTO postDetail = postDetailService.getPostDetailByUsernameAndPostname(username, postname, userId);

        model.addAttribute("post", postDetail);
        model.addAttribute("userId", userId);

        if (postDetail.getSeriesName() != null) {
            List<PostSummaryDTO> seriesPosts = postDetailService.getSeriesPostsBySeriesName(postDetail.getSeriesName());
            model.addAttribute("seriesPosts", seriesPosts);
        }



        return "postDetail";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id,
                                @RequestParam Long userId,
                                Model model) {
        PostDetailViewDTO postDetail = postDetailService.getPostDetail(id, userId);
        model.addAttribute("post", postDetail);
        return "edit-post";
    }

//    @PostMapping("/edit/{id}")
//    public String editPost(@PathVariable Long id,
//                           @ModelAttribute PostDetailViewDTO updatedPost,
//                           @RequestParam Long userId) {
//        postDetailService.updatePost(id, updatedPost, userId);
//        return "redirect:/vlog.io/@" + updatedPost.getAuthorName() + "/" + updatedPost.getTitle();
//    }
}