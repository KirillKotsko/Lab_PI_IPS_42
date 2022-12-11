package ua.kotsko.project.Examinator.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.kotsko.project.Examinator.models.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>  {
	Page<Question> findByExamId(Long examId, Pageable pageable);
    Optional<Question> findByIdAndExamId(Long id, Long examId);
}
