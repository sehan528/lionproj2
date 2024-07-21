package org.example.lionproj2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_detail_view")
@Immutable
@Getter
public class PostDetailView {
    @Id
    private Long id;
    private String title;
    private String context;
    private String authorName;
    @Column(name="author_id")
    private String authorId;
    @Column(name="author_profile_img")
    private String authorProfileImg;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private String thumbnailUrl;
//    private Boolean isPrivate;
    private Integer likeCount;
    private String tags;
    private String seriesName;
//    private Integer seriesOrder;
}