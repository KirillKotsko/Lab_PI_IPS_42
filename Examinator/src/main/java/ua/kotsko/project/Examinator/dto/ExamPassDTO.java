package ua.kotsko.project.Examinator.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import ua.kotsko.project.Examinator.models.Exam;
import ua.kotsko.project.Examinator.models.Question;

@Data
public class ExamPassDTO {

	private Long userId;
	
	private Long examId;
	
	private String name;
	
	private Integer duration;
	
	private List<Question> questions;
	
	public ExamPassDTO() {
		
	}
	
	public ExamPassDTO(Exam exam) {
		this.examId = exam.getId();
		this.name = exam.getName();
		this.duration = exam.getDurationMinute();
		this.questions = new ArrayList<>(exam.getQuestions());
	}
}
