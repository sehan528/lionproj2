package org.example.lionproj2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostUpdateDTO {
    private String title;
    private String context;
    private Set<String> tags;
    private boolean isPrivate;

    // getters and setters
}