package org.example.lionproj2.mapper;

import org.example.lionproj2.dto.RecentPostDTO;
import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public TrendingPostDTO postToTrendingPostDTO(Post post) {
        return TrendingPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .userName(post.getUsers().stream().findFirst().map(User::getName).orElse("Unknown"))
                .updateDate(post.getUpdateDate())
                .likeCount(post.getLikes().size())
                .build();
    }

    public RecentPostDTO postToRecentPostDTO(Post post) {
        return RecentPostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .userName(post.getUsers().stream().findFirst().map(User::getName).orElse("Unknown"))
                .creationDate(post.getCreationDate())
                .build();
    }
}