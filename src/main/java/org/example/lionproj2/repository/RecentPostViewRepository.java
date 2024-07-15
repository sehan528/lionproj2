package org.example.lionproj2.repository;

import org.example.lionproj2.entity.RecentPostView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentPostViewRepository extends JpaRepository<RecentPostView, Long> {
    Page<RecentPostView> findByUserIdOrderByViewDateDesc(Long userId, Pageable pageable);
    void deleteByUserIdAndPostId(Long userId, Long postId);

}