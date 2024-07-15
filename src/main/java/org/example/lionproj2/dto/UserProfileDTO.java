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
    private String email;
    private List<PostSummaryDTO> posts;
    private List<SeriesDTO> series;
    private String aboutMe;
    private List<TagDTO> tags;
}



