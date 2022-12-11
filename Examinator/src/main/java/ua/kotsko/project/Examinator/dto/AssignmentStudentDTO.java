package ua.kotsko.project.Examinator.dto;

import java.util.List;

import lombok.Data;

@Data
public class AssignmentStudentDTO {

	private Long userId;
	private String description;
	private ExamDTO exam; 
	
}
