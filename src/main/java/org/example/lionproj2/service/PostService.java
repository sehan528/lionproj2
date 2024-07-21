package org.example.lionproj2.service;

import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.PostForm;
import org.example.lionproj2.dto.PostUpdateDTO;
import org.example.lionproj2.entity.*;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.exception.UnauthorizedException;
import org.example.lionproj2.repository.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final SeriesRepository seriesRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));
    }

    @Transactional
    public Long savePost(Long userId, PostForm postForm) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 썸네일 URL이 없으면 기본 이미지 설정
        String thumbnailUrl = postForm.getThumbnailUrl();
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            thumbnailUrl = "https://images.velog.io/images/kim-mg/post/b6928585-e245-4e5f-b878-0bbf278e5886/velog_logo.png";
        }

        Post post = Post.builder()
                .title(postForm.getTitle())
                .context(postForm.getContext())
                .isPrivate(postForm.isPrivate())
                .thumbnailUrl(thumbnailUrl) // ...
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .author(author)
                .build();

        // 태그 처리
        Set<Tag> tags = postForm.getTags().stream()
                .limit(3)
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        }))
                .collect(Collectors.toSet());
        post.setTags(tags);

        // 시리즈 처리
        if (postForm.getSeriesName() != null && !postForm.getSeriesName().isEmpty()) {
            Series series = seriesRepository.findByName(postForm.getSeriesName())
                    .orElseGet(() -> {
                        Series newSeries = new Series();
                        newSeries.setName(postForm.getSeriesName());
                        return seriesRepository.save(newSeries);
                    });
            PostSeries postSeries = new PostSeries();

            postSeries.setPost(post);
            postSeries.setSeries(series);
            post.setPostSeries(Collections.singletonList(postSeries));
        }

        // 이미지 처리
        if (postForm.getImageUrls() != null && !postForm.getImageUrls().isEmpty()) {
            Set<Image> images = postForm.getImageUrls().stream()
                    .map(url -> {
                        Image image = new Image();
                        image.setUrl(url);
                        return imageRepository.save(image);
                    })
                    .collect(Collectors.toSet());
            post.setImages(images);
        }

        return postRepository.save(post).getId();
    }

    public void tempSavePost(Long userId, PostForm postForm) {
        // LocalStorage에 저장하는 로직 구현 (프론트엔드에서 처리)
    }

    public PostForm loadTempPost(Long userId) {
        // LocalStorage에서 불러오는 로직 구현 (프론트엔드에서 처리)
        return new PostForm(); // 실제로는 저장된 데이터를 반환
    }

    public List<String> getUserSeries(Long userId) {
        return seriesRepository.findSeriesNamesByUserId(userId);
    }

    public Post getPostByUsernameAndTitle(String username, String title) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return postRepository.findByAuthorAndTitle(user, title)
                .orElse(null);
    }

    @Transactional
    public boolean updatePost(String userId, String originalTitle, PostUpdateDTO updatedPost) {
        log.info("업데이트 포스트 [PostService]");
        log.info("userId: {}, originalTitle: {}, updatedTitle: {}", userId, originalTitle, updatedPost.getTitle());

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));

        log.info("유저: {} [PostService]", user);

        Post post = postRepository.findByAuthorAndTitle(user, originalTitle)
                .orElseThrow(() -> new PostNotFoundException("Post not found: " + originalTitle));

        log.info("포스트: {} [PostService]", post);

        post.setTitle(updatedPost.getTitle());
        post.setContext(updatedPost.getContext());
        post.setUpdateDate(LocalDateTime.now());

        // 태그 업데이트
        Set<Tag> tags = new HashSet<>();
        for (String tagName : updatedPost.getTags()) {
            // 태그 이름에서 불필요한 문자 제거 및 트림 (init 브라켓 , 삭제 버튼)
            String cleanedTagName = tagName.replaceAll("[\\[\\]\"×]", "").trim();

            if (!cleanedTagName.isEmpty()) {
                Tag tag = tagRepository.findByName(cleanedTagName)
                        .orElseGet(() -> tagRepository.save(new Tag(cleanedTagName)));
                tags.add(tag);
            }
        }
        post.setTags(tags);

        log.info("태그: {}, 포스트: {}", tags, post);

        postRepository.save(post);
        log.info("Post updated: userId={}, oldTitle={}, newTitle={}",
                userId, originalTitle, updatedPost.getTitle());
        return true;
    }




}
