package org.example.lionproj2.repository;

import org.example.lionproj2.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p FROM Post p JOIN FETCH p.users WHERE p.isPrivate = false AND p.updateDate >= :startDate ORDER BY SIZE(p.likes) DESC")
    List<Post> findTrendingPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("SELECT DISTINCT p FROM Post p JOIN FETCH p.users WHERE p.isPrivate = false ORDER BY p.creationDate DESC")
    List<Post> findRecentPosts(Pageable pageable);
}