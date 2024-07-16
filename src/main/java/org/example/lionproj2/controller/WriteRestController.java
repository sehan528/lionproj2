package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.PostForm;
import org.example.lionproj2.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/write")
@RequiredArgsConstructor
public class WriteRestController {
    private final PostService postService;

    @PostMapping("/temp-save")
    public ResponseEntity<Void> tempSave(@RequestBody PostForm postForm, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        postService.tempSavePost(userId, postForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/temp-load")
    public ResponseEntity<PostForm> tempLoad(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        PostForm postForm = postService.loadTempPost(userId);
        return ResponseEntity.ok(postForm);
    }
}