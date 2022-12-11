package ua.kotsko.project.Examinator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kotsko.project.Examinator.models.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
}
