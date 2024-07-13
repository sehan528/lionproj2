package org.example.lionproj2.repository;

import org.example.lionproj2.entity.LikeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeViewRepository extends JpaRepository<LikeView, Long> {
    Boolean existsByUserIdAndPostId(Long userId, Long postId);
    Optional<LikeView> findByUserIdAndPostId(Long userId, Long postId);
}