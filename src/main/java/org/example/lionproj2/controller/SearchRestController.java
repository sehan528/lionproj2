package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.SearchResultDTO;
import org.example.lionproj2.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchRestController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResultDTO> searchMore(
            @RequestParam String q,
            @RequestParam int page,
            @RequestParam(defaultValue = "20") int size) {

        SearchResultDTO result = searchService.search(q.toLowerCase(), page, size);
        return ResponseEntity.ok(result);
    }
}