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
    private String title;
    private String thumbnailUrl;
    private String userName;
    private LocalDateTime creationDate;
}