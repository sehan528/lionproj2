package org.example.lionproj2.mapper;

import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.SeriesDTO;
import org.example.lionproj2.dto.TagDTO;
import org.example.lionproj2.dto.UserProfileDTO;

import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.Series;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.entity.Tag;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserProfileMapper {

    public UserProfileDTO toUserProfileDTO(User user, List<PostSummaryDTO> posts, List<SeriesDTO> series, String aboutMe) {
        Map<String, Integer> tagCount = new HashMap<>();
        posts.forEach(post -> post.getTags().forEach(tag -> tagCount.merge(tag, 1, Integer::sum)));

        List<TagDTO> tags = tagCount.entrySet().stream()
                .map(entry -> new TagDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(TagDTO::getCount).reversed())
                .collect(Collectors.toList());

        return UserProfileDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .profileImg(user.getProfileImg())
                .posts(posts)
                .series(series)
                .aboutMe(aboutMe)
                .tags(tags)
                .build();
    }

    public PostSummaryDTO toPostSummaryDTO(Post post) {
        return PostSummaryDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .thumbnailUrl(post.getThumbnailUrl())
                .context(post.getContext())
                .creationDate(post.getCreationDate())
                .tags(post.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .isPrivate(post.isPrivate())
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