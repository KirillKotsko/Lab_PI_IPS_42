package ua.kotsko.project.Examinator.dto;

import lombok.Data;

@Data
public class ResultDTO {
	
	private String examName;
	
	private Long mark;
	
	private String when;
	
	public ResultDTO() {
		
	}

	public ResultDTO(String examName, Long mark, String when) {
		super();
		this.examName = examName;
		this.mark = mark;
		this.when = when;
	}	
}
