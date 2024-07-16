package org.example.lionproj2.service;

import org.example.lionproj2.dto.PostForm;
import org.example.lionproj2.entity.*;
import org.example.lionproj2.exception.PostNotFoundException;
import org.example.lionproj2.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


}
