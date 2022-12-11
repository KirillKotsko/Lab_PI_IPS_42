package ua.kotsko.project.Examinator.dto;

import lombok.Data;
import ua.kotsko.project.Examinator.models.Difficulty;
import ua.kotsko.project.Examinator.models.Exam;
import ua.kotsko.project.Examinator.models.Subject;

@Data
public class ExamDTO {

	private Long id;
	private String name;
	private Difficulty level;
	private Subject subject;
	private int duration;
	private Boolean choose = false;
	
	public ExamDTO() {
		
	}
	
	public ExamDTO(Exam exam) {
		this.id = exam.getId();
		this.name = exam.getName();
		this.level = exam.getLevel();
		this.subject = exam.getSubject();
		this.duration = exam.getDurationMinute();
	}
	
}
