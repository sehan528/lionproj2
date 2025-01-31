package org.example.lionproj2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recent_views")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "view_date")
    private LocalDateTime viewDate;
}