package org.example.lionproj2.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.exception.InvalidPeriodException;
import org.example.lionproj2.service.TrendingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/trending")
@RequiredArgsConstructor
public class TrendingRestController {

    private final TrendingService trendingService;


    @GetMapping("/{period}")
    public ResponseEntity<List<TrendingPostDTO>> getTrendingPosts(
            @PathVariable String period,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {

        log.info("Get trending posts for {} with period {}", period, page);

        List<TrendingPostDTO> posts = trendingService.getTrendingPosts(period, page, size);
        log.info("{} posts found", posts.size());
        return ResponseEntity.ok(posts);
    }
}



//    @GetMapping("/trending/{period}")
//    public ResponseEntity<?> getTrendingPosts(
//            @PathVariable String period,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "20") int size) {
//        // 이것도 서비스에서 핸들러 타게 고쳐야됩니다!
//        try {
//            List<TrendingPostDTO> trendingPosts = trendingService.getTrendingPosts(period, page, size);
//            return ResponseEntity.ok(trendingPosts);
//        } catch (InvalidPeriodException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }