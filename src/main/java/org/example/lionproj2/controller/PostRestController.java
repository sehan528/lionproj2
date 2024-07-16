package org.example.lionproj2.controller;

import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.service.PostDetailService;
import org.example.lionproj2.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
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
                                              @RequestParam Long userId) {
        Boolean isLiked = postDetailService.toggleLike(userId, id);
        return ResponseEntity.ok(isLiked);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @RequestParam Long userId) {
        postDetailService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

}