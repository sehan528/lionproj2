package org.example.lionproj2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultDTO {
    private List<PostSummaryDTO> posts;
    private Long totalCount;
}
