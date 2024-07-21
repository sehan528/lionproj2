package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailViewDTO {
    private Long id;
    private String title;
    private String context;
    private String authorName;
    private String authorId;
    private String authorProfileImg;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String thumbnailUrl;
//    private Boolean isPrivate;
    private Integer likeCount;
    private List<String> tags;
    private String seriesName;
//    private Integer seriesOrder;
    private Boolean isLiked;
}