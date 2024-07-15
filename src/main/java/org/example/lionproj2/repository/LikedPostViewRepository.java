package org.example.lionproj2.repository;

import org.example.lionproj2.entity.LikedPostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedPostViewRepository extends JpaRepository<LikedPostView, Long> {
    Page<LikedPostView> findByAuthorNameOrderByLikedAtDesc(String authorName, Pageable pageable);
}