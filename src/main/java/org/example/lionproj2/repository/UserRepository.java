package org.example.lionproj2.repository;

import org.example.lionproj2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.userId = :userId")
    Optional<User> findByUserIdWithRoles(@Param("userId") String userId);
}