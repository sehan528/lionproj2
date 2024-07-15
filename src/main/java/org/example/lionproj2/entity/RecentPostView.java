package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "recent_posts_view")
@Getter
@Setter
@Immutable
public class RecentPostView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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