package ua.kotsko.project.Examinator.utils;

import java.util.List;

import ua.kotsko.project.Examinator.dto.ExamPassDTO;
import ua.kotsko.project.Examinator.models.Question;

public class ReadyToPassUtil {
	
	public static void prepare(ExamPassDTO exam) {
		List<Question> questions = exam.getQuestions();
		questions.stream()
			.forEach(x -> x.getAnswers()
					.stream().forEach(y -> y.setCorrect(false)));
	}

}
