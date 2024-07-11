package org.example.lionproj2.controller;

import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.exception.InvalidPeriodException;
import org.example.lionproj2.service.TrendingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TrendingRestController {

    private final TrendingService trendingService;

    @GetMapping("/trending/{period}")
    public ResponseEntity<?> getTrendingPosts(
            @PathVariable String period,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            List<TrendingPostDTO> trendingPosts = trendingService.getTrendingPosts(period, page, size);
            return ResponseEntity.ok(trendingPosts);
        } catch (InvalidPeriodException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}