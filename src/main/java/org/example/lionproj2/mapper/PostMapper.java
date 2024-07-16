package org.example.lionproj2.mapper;

import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.dto.TrendingPostDTO;

import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.Tag;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
// likeCount 집계하는 방법 도안해서 builder 에 붙여야 합니다!
public class PostMapper {

    public TrendingPostDTO postToTrendingPostDTO(Post post) {
        return TrendingPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .context(post.getContext())
                .authorName(post.getAuthor().getName())
                .profile_img(post.getAuthor().getProfileImg())
                .createdAt(post.getCreationDate())
                .updateDate(post.getUpdateDate())
                .likeCount(post.getLikes() != null ? post.getLikes().size() : 0)
                .build();
    }

    public RecentPostDTO postToRecentPostDTO(Post post) {
        return RecentPostDTO.builder()
//                .id(post.getId())
//                .title(post.getTitle())
//                .thumbnailUrl(post.getThumbnailUrl())
//                .authorName(post.getAuthor().getName())
//                .creationDate(post.getCreationDate())
//                .build();
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .context(post.getContext())
                .authorName(post.getAuthor().getName())
                .profile_img(post.getAuthor().getProfileImg())
                .creationDate(post.getCreationDate())
                .build();
    }

    public PostSummaryDTO toPostSummaryDTO(Post post) {
        return PostSummaryDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .authorName(post.getAuthor().getName())
                .creationDate(post.getCreationDate())
                .tags(post.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

}