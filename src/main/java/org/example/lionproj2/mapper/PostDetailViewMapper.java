package org.example.lionproj2.mapper;

import org.example.lionproj2.dto.PostDetailViewDTO;
import org.example.lionproj2.entity.PostDetailView;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PostDetailViewMapper {
    public PostDetailViewDTO toPostDetailViewDTO(PostDetailView view, Boolean isLiked) {
        return PostDetailViewDTO.builder()
                .id(view.getId())
                .title(view.getTitle())
                .context(view.getContext())
                .authorName(view.getAuthorName())
                .creationDate(view.getCreationDate())
                .updateDate(view.getUpdateDate())
                .thumbnailUrl(view.getThumbnailUrl())
                .isPrivate(view.getIsPrivate())
                .likeCount(view.getLikeCount())
                .tags(Arrays.asList(view.getTags().split(",")))
                .seriesName(view.getSeriesName())
//                .seriesOrder(view.getSeriesOrder())
                .isLiked(isLiked)
                .build();
    }
}