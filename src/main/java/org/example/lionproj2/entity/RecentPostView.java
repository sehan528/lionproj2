package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "recent_posts_view")
@Getter
@Setter
public class RecentPostView {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "post_id", insertable = false, updatable = false)
    private Long postId;

    @Column(insertable = false, updatable = false)
    private String title;

    @Column(insertable = false, updatable = false)
    private String thumbnailUrl;

    @Column(insertable = false, updatable = false)
    private String authorName;

    @Column(insertable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(insertable = false, updatable = false)
    private LocalDateTime viewDate;
}