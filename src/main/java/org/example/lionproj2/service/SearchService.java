package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.SearchResultDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public SearchResultDTO search(String query, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> searchResult = postRepository.searchPosts(query, pageRequest);

        List<PostSummaryDTO> posts = searchResult.getContent().stream()
                .map(postMapper::toPostSummaryDTO)
                .collect(Collectors.toList());

        return SearchResultDTO.builder()
                .posts(posts)
                .totalCount(searchResult.getTotalElements())
                .build();
    }
}