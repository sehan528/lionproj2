package org.example.lionproj2.repository;

import org.example.lionproj2.entity.PostDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDetailViewRepository extends JpaRepository<PostDetailView, Long> {
    Optional<PostDetailView> findByAuthorNameAndTitle(String authorName, String title);
    List<PostDetailView> findBySeriesNameOrderByCreationDateAsc(String seriesName);
}
