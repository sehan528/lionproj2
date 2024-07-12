package org.example.lionproj2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeriesDTO {
    private Long id;
    private String name;
    private List<PostSummaryDTO> posts;
}