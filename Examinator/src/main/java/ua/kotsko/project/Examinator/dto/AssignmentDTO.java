package ua.kotsko.project.Examinator.dto;

import java.util.List;

import lombok.Data;

@Data
public class AssignmentDTO {
	
	private Long userId;
	private String description;
	private List<ExamDTO> exams; 
}
