package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.entity.LikeView;
import org.example.lionproj2.entity.PostDetailView;
import org.example.lionproj2.entity.RecentView;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.exception.UnauthorizedException;
import org.example.lionproj2.mapper.PostDetailViewMapper;
import org.example.lionproj2.repository.LikeViewRepository;
import org.example.lionproj2.repository.PostDetailViewRepository;
import org.example.lionproj2.repository.RecentViewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostDetailService {
    private final PostDetailViewRepository postDetailViewRepository;
    private final PostDetailViewMapper postDetailViewMapper;
    private final LikeViewRepository likeViewRepository;
    private final RecentViewRepository recentViewRepository;

    public PostDetailViewDTO getPostDetail(Long postId, Long userId) {
        PostDetailView postDetailView = postDetailViewRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        Boolean isLiked = likeViewRepository.existsByUserIdAndPostId(userId, postDetailView.getId());

        updateRecentView(userId, postDetailView.getId());

        return postDetailViewMapper.toPostDetailViewDTO(postDetailView, isLiked);
    }

    public PostDetailViewDTO getPostDetailByUsernameAndPostname(String username, String postname, Long userId) {
        PostDetailView postDetailView = postDetailViewRepository.findByAuthorNameAndTitle(username, postname)
                .orElseThrow(() -> new PostNotFoundException("Post not found: " + postname));

        Boolean isLiked = likeViewRepository.existsByUserIdAndPostId(userId, postDetailView.getId());

        updateRecentView(userId, postDetailView.getId());

        return postDetailViewMapper.toPostDetailViewDTO(postDetailView, isLiked);
    }

    @Transactional(readOnly = true)
    public List<PostSummaryDTO> getSeriesPostsBySeriesName(String seriesName) {
        List<PostDetailView> seriesPosts = postDetailViewRepository.findBySeriesNameOrderByCreationDateAsc(seriesName);
        return seriesPosts.stream()
                .map(this::mapToPostSummaryDTO)
                .collect(Collectors.toList());
    }

    private PostSummaryDTO mapToPostSummaryDTO(PostDetailView postDetailView) {
        return PostSummaryDTO.builder()
                .id(postDetailView.getId())
                .title(postDetailView.getTitle())
                .authorName(postDetailView.getAuthorName())
                .creationDate(postDetailView.getCreationDate())
                .build();
    }

    @Transactional
    public void updateRecentView(Long userId, Long postId) {
        RecentView lastView = recentViewRepository.findTopByUserIdOrderByViewDateDesc(userId)
                .orElse(null);

        if (lastView == null || !lastView.getPostId().equals(postId)) {
            RecentView newView = new RecentView();
            newView.setUserId(userId);
            newView.setPostId(postId);
            newView.setViewDate(LocalDateTime.now());
            recentViewRepository.save(newView);

            List<RecentView> userViews = recentViewRepository.findByUserIdOrderByViewDateDesc(userId);
            if (userViews.size() > 20) {
                recentViewRepository.delete(userViews.get(userViews.size() - 1));
            }
        }
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        PostDetailView post = postDetailViewRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        if (!post.getId().equals(userId)) {
            throw new UnauthorizedException("User not authorized to delete this post");
        }

        postDetailViewRepository.deleteById(postId);
    }

//    @Transactional
//    public void updatePost(Long postId, PostDetailViewDTO updatedPost, Long userId) {
//        PostDetailView existingPost = postDetailViewRepository.findById(postId)
//                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
//
//        if (!existingPost.getId().equals(userId)) {
//            throw new UnauthorizedException("User not authorized to update this post");
//        }
//
//        // 업데이트 로직 구현
//        existingPost.setTitle(updatedPost.getTitle());
//        existingPost.setContext(updatedPost.getContext());
//        // 다른 필드들도 필요에 따라 업데이트
//
//        postDetailViewRepository.save(existingPost);
//    }

    @Transactional
    public Boolean toggleLike(Long userId, Long postId) {
        Optional<LikeView> existingLike = likeViewRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            likeViewRepository.delete(existingLike.get());
            return false;
        } else {
            LikeView newLike = new LikeView();
            newLike.setUserId(userId);
            newLike.setPostId(postId);
            likeViewRepository.save(newLike);
            return true;
        }
    }
}