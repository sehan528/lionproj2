package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.service.RecentPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recent")
@RequiredArgsConstructor
public class RecentPostRestController {
    private final RecentPostService recentPostService;

    @GetMapping
    public ResponseEntity<List<RecentPostDTO>> getRecentPosts(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        List<RecentPostDTO> recentPosts = recentPostService.getRecentPosts(userId, page, size);
        return ResponseEntity.ok(recentPosts);
    }
}