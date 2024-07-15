package org.example.lionproj2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "recent_posts_view")
@Getter
@Setter
public class RecentPostView {
    @Id
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    private String title;
    private String thumbnailUrl;
    private String authorName;
    private LocalDateTime creationDate;
    private LocalDateTime viewDate;
}