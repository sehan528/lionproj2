package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrendingPostDTO {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String context;
    private String authorName;
    private String profile_img;
    private LocalDateTime createdAt;
    private LocalDateTime updateDate;
    private Integer likeCount;
}