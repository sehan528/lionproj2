package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.entity.RecentPostView;
import org.example.lionproj2.entity.RecentView;
import org.example.lionproj2.repository.RecentPostViewRepository;
import org.example.lionproj2.repository.RecentViewRepository;
import org.example.lionproj2.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecentPostService {
    private final RecentPostViewRepository recentPostViewRepository;
    private final UserRepository userRepository;
    private final RecentViewRepository recentViewRepository;

    @Transactional(readOnly = true)
    public List<RecentPostDTO> getRecentPosts(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RecentPostView> recentPostsPage = recentPostViewRepository.findByUserIdOrderByViewDateDesc(userId, pageRequest);

        List<RecentPostDTO> recentPosts = recentPostsPage.getContent().stream()
                .map(this::mapToRecentPostDTO)
                .collect(Collectors.toList());

        log.info("Retrieved {} recent posts for user ID: {}", recentPosts.size(), userId);

        return recentPosts;
    }

    private RecentPostDTO mapToRecentPostDTO(RecentPostView view) {
        return RecentPostDTO.builder()
                .id(view.getId())
                .postId(view.getPostId())
                .title(view.getTitle())
                .thumbnailUrl(view.getThumbnailUrl())
                .authorName(view.getAuthorName())
                .profile_img(view.getProfile_img())
                .creationDate(view.getCreationDate())
                .viewDate(view.getViewDate())
                .build();
    }

//    @Transactional
//    public void addRecentView(Long userId, Long postId) {
//        // 이미 존재하는 최근 조회 기록이 있다면 삭제
//        recentPostViewRepository.deleteByUserIdAndPostId(userId, postId);
//
//        RecentPostView recentPostView = new RecentPostView();
//        recentPostView.setUserId(userId);
//        recentPostView.setPostId(postId);
//        recentPostView.setViewDate(LocalDateTime.now());
//
//        recentPostViewRepository.save(recentPostView);
//    }

    @Transactional
    public void addRecentView(Long userId, Long postId) {
        RecentView recentView = new RecentView();
        recentView.setUserId(userId);
        recentView.setPostId(postId);
        recentView.setViewDate(LocalDateTime.now());
        recentViewRepository.save(recentView);

        // 최대 20개의 최근 조회 기록 유지
        List<RecentView> userViews = recentViewRepository.findByUserIdOrderByViewDateDesc(userId);
        if (userViews.size() > 20) {
            recentViewRepository.delete(userViews.get(userViews.size() - 1));
        }
    }
}