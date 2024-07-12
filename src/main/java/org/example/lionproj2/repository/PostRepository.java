package org.example.lionproj2.repository;

import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByIsPrivateFalseAndUpdateDateGreaterThanEqualOrderByLikesDesc(LocalDateTime startDate, Pageable pageable);
    List<Post> findByIsPrivateFalseOrderByCreationDateDesc(Pageable pageable);

    List<Post> findByAuthorOrderByCreationDateDesc(User author);
}






