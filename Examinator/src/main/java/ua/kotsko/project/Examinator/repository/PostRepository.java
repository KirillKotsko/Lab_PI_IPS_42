package ua.kotsko.project.Examinator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kotsko.project.Examinator.models.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts p WHERE p.restriction = :restriction", nativeQuery = true)
    List<Post> searchAllByRestriction(@Param("restriction") String restriction);

}
