package org.example.lionproj2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "series")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}