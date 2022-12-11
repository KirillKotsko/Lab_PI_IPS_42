package ua.kotsko.project.Examinator.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class ResultExam {

	private Long userId;
	
	private Long examId;
	
	private Long mark;
	
	private String when;
	
	public void setCurrentDate() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		when = formater.format(new Date(System.currentTimeMillis()));
	}
}
