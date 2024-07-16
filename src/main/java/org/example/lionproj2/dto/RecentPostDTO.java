package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentPostDTO {
    private Long id;
    private Long postId;
    private String title;
    private String thumbnailUrl;
    private String context;
    private String authorName;
    private LocalDateTime creationDate;
    private String profile_img;
    private LocalDateTime viewDate;
}