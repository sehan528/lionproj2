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

    private Long postId; // 추가됨.

    private String title;
    private String thumbnailUrl;
    private String authorName;
    private LocalDateTime creationDate;

    private LocalDateTime viewDate; // 추가됨.
}