package org.example.lionproj2.repository;

import org.example.lionproj2.entity.AboutMe;
import org.example.lionproj2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AboutMeRepository extends JpaRepository<AboutMe, Long> {
    Optional<AboutMe> findByUser(User user);
}