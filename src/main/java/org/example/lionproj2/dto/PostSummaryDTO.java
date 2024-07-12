package org.example.lionproj2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSummaryDTO {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private LocalDateTime creationDate;
}