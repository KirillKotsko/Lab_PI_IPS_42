package ua.kotsko.project.Examinator.services;

import java.util.List;

import ua.kotsko.project.Examinator.dto.AssignmentStudentDTO;
import ua.kotsko.project.Examinator.dto.ExamPassDTO;
import ua.kotsko.project.Examinator.dto.ResultDTO;
import ua.kotsko.project.Examinator.models.Assigment;
import ua.kotsko.project.Examinator.models.ResultExam;


public interface StudentService {
	
	public List<ResultExam> findByUserId(Long id);

	public void save(ResultExam resultExam);

	public List<ResultDTO> findByUserIdDTO(Long id);

	public List<Assigment> findByUsernameAssignDTO(String name);

	public void checkAndSaveResult(ExamPassDTO exam);

	public ExamPassDTO getExamPassDTO(Long id, String name);

	public List<AssignmentStudentDTO> convertTOAssignStudDTO(List<Assigment> assignments);
	
}
