package org.example.lionproj2.repository;

import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByIsPrivateFalseAndUpdateDateGreaterThanEqualOrderByLikesDesc(LocalDateTime startDate, Pageable pageable);
    List<Post> findByIsPrivateFalseOrderByCreationDateDesc(Pageable pageable);

    List<Post> findByAuthorOrderByCreationDateDesc(User author);


    // 검색 기능. 추후에 Injection 고려할 수 있도록 하자.
    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.context) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);

    // post period search
    @Query("SELECT p FROM Post p WHERE p.updateDate >= :startDate ORDER BY p.creationDate DESC")
    List<Post> findTrendingPosts(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}





