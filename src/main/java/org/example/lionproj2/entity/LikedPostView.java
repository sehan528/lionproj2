package org.example.lionproj2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "liked_posts_view")
@Getter
@Setter
public class LikedPostView {
    @Id
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String authorName;
    private LocalDateTime creationDate;
    private LocalDateTime likedAt;
}