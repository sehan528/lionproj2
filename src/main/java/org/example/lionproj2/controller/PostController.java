package org.example.lionproj2.controller;

import org.example.lionproj2.entity.Post;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.service.PostService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        // 여기도 서비스에서 핸들러 타게 고쳐야됩니다!
        try {
            Post post = postService.getPostById(id);
            return ResponseEntity.ok(postMapper.postToTrendingPostDTO(post));
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}