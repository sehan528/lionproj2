package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_series")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;
}