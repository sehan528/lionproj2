package org.example.lionproj2.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.LikeRequestDTO;
import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.dto.PostUpdateDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.service.PostDetailService;
import org.example.lionproj2.service.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostRestController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final PostDetailService postDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id, @RequestParam Long userId) {
        try {
            PostDetailViewDTO post = postDetailService.getPostDetail(id, userId);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Boolean> toggleLike(@PathVariable Long id,
                                              @RequestBody LikeRequestDTO likeRequest) {
        log.info("Received like toggle request for postId: {}, userId: {}", id, likeRequest.getUserId());
        Boolean isLiked = postDetailService.toggleLike(likeRequest.getUserId(), id);
        log.info("Like toggle result for postId: {}, userId: {}: {}", id, likeRequest.getUserId(), isLiked);
        return ResponseEntity.ok(isLiked);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @RequestParam Long userId) {
        postDetailService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{id}/edit")
//    public ResponseEntity<?> editPost(@PathVariable Long id,
//                                      @RequestBody PostUpdateDTO updatedPost,
//                                      Authentication authentication) {
//        String authenticatedUserId = authentication.getName();
//        boolean success = postService.updatePost(id, updatedPost, authenticatedUserId);
//        if (success) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().body("Failed to update post");
//        }
//    }



}