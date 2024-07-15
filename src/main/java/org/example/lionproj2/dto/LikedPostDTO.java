package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikedPostDTO {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String authorName;
    private LocalDateTime creationDate;
    private LocalDateTime likedAt;
}