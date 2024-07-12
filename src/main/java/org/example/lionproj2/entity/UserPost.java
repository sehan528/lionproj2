package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}