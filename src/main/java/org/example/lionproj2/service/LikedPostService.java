package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
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
public class LikedPostService {
    private final LikedPostViewRepository likedPostViewRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LikedPostDTO> getLikedPosts(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LikedPostView> likedPostsPage = likedPostViewRepository.findByAuthorNameOrderByLikedAtDesc(user.getName(), pageRequest);

        return likedPostsPage.getContent().stream()
                .map(this::mapToLikedPostDTO)
                .collect(Collectors.toList());
    }

    private LikedPostDTO mapToLikedPostDTO(LikedPostView likedPostView) {
        return LikedPostDTO.builder()
                .id(likedPostView.getId())
                .title(likedPostView.getTitle())
                .thumbnailUrl(likedPostView.getThumbnailUrl())
                .authorName(likedPostView.getAuthorName())
                .creationDate(likedPostView.getCreationDate())
                .likedAt(likedPostView.getLikedAt())
                .build();
    }
}