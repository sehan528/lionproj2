package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.LikedPostDTO;
import org.example.lionproj2.entity.LikedPostView;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.repository.LikedPostViewRepository;
import org.example.lionproj2.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikedPostService {
    private final LikedPostViewRepository likedPostViewRepository;

    @Transactional(readOnly = true)
    public List<LikedPostDTO> getLikedPosts(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LikedPostView> likedPostsPage = likedPostViewRepository.findByUserIdOrderByLikedAtDesc(userId, pageRequest);

        List<LikedPostDTO> likedPosts = likedPostsPage.getContent().stream()
                .map(this::mapToLikedPostDTO)
                .collect(Collectors.toList());

        log.info("Retrieved {} liked posts for user ID: {}", likedPosts.size(), userId);

        return likedPosts;
    }

    private LikedPostDTO mapToLikedPostDTO(LikedPostView view) {
        return LikedPostDTO.builder()
                .id(view.getPostId())
                .title(view.getTitle())
                .thumbnailUrl(view.getThumbnailUrl())
                .authorName(view.getAuthorName())
                .creationDate(view.getCreationDate())
                .likedAt(view.getLikedAt())
                .build();
    }
}