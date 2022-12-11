package ua.kotsko.project.Examinator.services;

import java.util.List;

import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.models.Exam;

public interface ExamManagerService {

	public void saveExam(Exam exam);
	public Exam getExam(Long id);
	public void deleteExam(Long id);
	public List<Exam> getExams();
	public List<ExamDTO> getExamsDTO();
	public List<ExamDTO> getExamsDTO(String query);
	public List<ExamDTO> getExamsDTO(String substring, String substring2);
}
