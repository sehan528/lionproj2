package org.example.lionproj2.repository;

import org.example.lionproj2.entity.Series;
import org.example.lionproj2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByPostSeries_Post_AuthorOrderByName(User author);
    Optional<Series> findByName(String name);

    @Query("SELECT DISTINCT s.name FROM Series s JOIN s.postSeries ps JOIN ps.post p WHERE p.author.id = :userId")
    List<String> findSeriesNamesByUserId(@Param("userId") Long userId);

}