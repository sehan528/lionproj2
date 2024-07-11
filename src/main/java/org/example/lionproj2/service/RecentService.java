package org.example.lionproj2.service;

import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecentService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<RecentPostDTO> getRecentPosts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return postRepository.findByIsPrivateFalseOrderByCreationDateDesc(pageRequest)
                .stream()
                .map(postMapper::postToRecentPostDTO)
                .collect(Collectors.toList());
    }
}