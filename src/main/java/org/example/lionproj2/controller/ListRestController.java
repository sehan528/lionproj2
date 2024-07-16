package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.LikedPostDTO;
import org.example.lionproj2.service.LikedPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lists")
@RequiredArgsConstructor
public class ListRestController {
    private final LikedPostService likedPostService;

    @GetMapping("/liked")
    public ResponseEntity<List<LikedPostDTO>> getLikedPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<LikedPostDTO> likedPosts = likedPostService.getLikedPosts(userId, page, size);

        return ResponseEntity.ok(likedPosts);
    }
}