package ua.kotsko.project.Examinator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.kotsko.project.Examinator.models.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>  {

}

