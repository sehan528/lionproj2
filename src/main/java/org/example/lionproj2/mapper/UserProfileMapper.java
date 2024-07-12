package org.example.lionproj2.mapper;

import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.SeriesDTO;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.Series;
import org.example.lionproj2.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProfileMapper {

    public UserProfileDTO toUserProfileDTO(User user, List<PostSummaryDTO> posts, List<SeriesDTO> series) {
        return UserProfileDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profileImg(user.getProfileImg())
                .posts(posts)
                .series(series)
                .build();
    }

    public PostSummaryDTO toPostSummaryDTO(Post post) {
        return PostSummaryDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .creationDate(post.getCreationDate())
                .build();
    }

    public SeriesDTO toSeriesDTO(Series series, List<PostSummaryDTO> posts) {
        return SeriesDTO.builder()
                .id(series.getId())
                .name(series.getName())
                .posts(posts)
                .build();
    }
}