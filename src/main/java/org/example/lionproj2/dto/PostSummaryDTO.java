package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummaryDTO {
    private Long id;
    private String title;
    private String authorName;
    private String authorProfileImg;
    private String thumbnailUrl;
    private String context;
    private LocalDateTime creationDate;

    private List<String> tags;
    private boolean isPrivate;

}