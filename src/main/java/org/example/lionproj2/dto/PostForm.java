package org.example.lionproj2.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostForm {
    private String title;
    private String context;
    private List<String> tags;
    private String thumbnailUrl;
    private boolean isPrivate;
    private String seriesName;
    private List<String> imageUrls;
}