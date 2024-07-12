package org.example.lionproj2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private String userId;
    private String name;
    private String profileImg;
    private List<PostSummaryDTO> posts;
    private List<SeriesDTO> series;
}



