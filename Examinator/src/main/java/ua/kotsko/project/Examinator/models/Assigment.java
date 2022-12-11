package ua.kotsko.project.Examinator.models;

import lombok.Data;

@Data
public class Assigment {
	
	private Long userId;
	
	private Long examId;
	
	private String description;
}
