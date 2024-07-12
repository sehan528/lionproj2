package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_me")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutMe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private String context;
}