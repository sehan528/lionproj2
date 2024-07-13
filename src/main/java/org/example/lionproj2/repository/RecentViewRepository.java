package org.example.lionproj2.repository;

import org.example.lionproj2.entity.RecentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    Optional<RecentView> findTopByUserIdOrderByViewDateDesc(Long userId);
    List<RecentView> findByUserIdOrderByViewDateDesc(Long userId);
}