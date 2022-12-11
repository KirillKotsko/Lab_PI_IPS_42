package ua.kotsko.project.Examinator.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.models.Exam;
import ua.kotsko.project.Examinator.models.Question;
import ua.kotsko.project.Examinator.repository.AssigmentRepository;
import ua.kotsko.project.Examinator.repository.ExamRepository;
import ua.kotsko.project.Examinator.repository.ResultExamRepository;
import ua.kotsko.project.Examinator.services.ExamManagerService;

@Service
@Transactional(readOnly = true)
public class ExamManagerServiceImpl implements ExamManagerService {

	private ExamRepository examRepository;
	
	@Autowired
	private AssigmentRepository assignRepository;
	
	@Autowired
	private ResultExamRepository resultRepository;
	
	@Autowired
	public ExamManagerServiceImpl(ExamRepository examRepository) {
		super();
		this.examRepository = examRepository;
	}

	@Override
	@Transactional
	public void saveExam(Exam exam) {
		examRepository.save(exam);
	}

	@Override
	public Exam getExam(Long id) {
		Optional<Exam> optionalExam = examRepository.findById(id);
		Exam exam = null;
        if (optionalExam.isPresent()) {
            exam = optionalExam.get();
            List<Question> distinctedQuestions = exam.getQuestions().stream().distinct().collect(Collectors.toList());
            exam.setQuestions(distinctedQuestions);
        } else {
            throw new RuntimeException("Exam not found for id : " + id);
        }
		return exam;
	}

	@Override
	@Transactional
	public void deleteExam(Long id) {
		try {
			resultRepository.deleteEI(id);
			assignRepository.deleteEI(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		examRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Exam> getExams() {
		List<Exam> exams = new ArrayList<>();
		examRepository.findAll().forEach(exams::add);
		return exams;
	}

	@Override
	@Transactional
	public List<ExamDTO> getExamsDTO() {
		return getExams().stream().map(x -> new ExamDTO(x)).toList();
	}

	@Override
	@Transactional
	public List<ExamDTO> getExamsDTO(String query) {
		return getExamsDTO().stream().filter(x -> 
			x.getName().toLowerCase().contains(query.toLowerCase()) ||
			x.getSubject().getTitle().toLowerCase().contains(query.toLowerCase()) ||
			x.getLevel().getLevel().toLowerCase().contains(query.toLowerCase())).toList();
	}

	@Override
	@Transactional
	public List<ExamDTO> getExamsDTO(String substring, String substring2) {
		List<ExamDTO> sorted = null;
		if (substring.equals("n") && substring2.equals("up"))
			sorted = getExamsDTO().stream().sorted(Comparator.comparing(ExamDTO::getName)).toList();
		else if (substring.equals("n") && substring2.equals("down"))
			sorted = getExamsDTO().stream().sorted(Comparator.comparing(ExamDTO::getName).reversed()).toList();
		else if (substring.equals("s") && substring2.equals("up"))
			sorted = getExamsDTO().stream().sorted(Comparator.comparing(ExamDTO::getSubject)).toList();
		else if (substring.equals("s") && substring2.equals("down"))
			sorted = getExamsDTO().stream().sorted(Comparator.comparing(ExamDTO::getSubject).reversed()).toList();
		return sorted;
	}


}
