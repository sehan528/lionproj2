package org.example.lionproj2.repository;

import org.example.lionproj2.entity.PostSeries;
import org.example.lionproj2.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSeriesRepository extends JpaRepository<PostSeries, Long> {
    List<PostSeries> findBySeriesOrderByPostCreationDateDesc(Series series);
}