package ua.kotsko.project.Examinator.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.kotsko.project.Examinator.dto.AssignmentDTO;
import ua.kotsko.project.Examinator.dto.AssignmentStudentDTO;
import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.dto.ExamPassDTO;
import ua.kotsko.project.Examinator.dto.ResultDTO;
import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Answer;
import ua.kotsko.project.Examinator.models.Assigment;
import ua.kotsko.project.Examinator.models.Exam;
import ua.kotsko.project.Examinator.models.Question;
import ua.kotsko.project.Examinator.models.ResultExam;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.repository.ResultExamRepository;
import ua.kotsko.project.Examinator.repository.AnswerRepository;
import ua.kotsko.project.Examinator.repository.AssigmentRepository;
import ua.kotsko.project.Examinator.services.ExamManagerService;
import ua.kotsko.project.Examinator.services.StudentService;
import ua.kotsko.project.Examinator.utils.ListShuffleUtil;
import ua.kotsko.project.Examinator.utils.ReadyToPassUtil;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

	@Autowired
	private ResultExamRepository resultRepository;
	
	@Autowired
	private AssigmentRepository assigmentRepository;  
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private ExamManagerService examService;
	
	@Override
	public List<ResultExam> findByUserId(Long id) {
		List<ResultExam> resultExams = null;
		try {
			resultExams = resultRepository.findByUserId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultExams;
	}

	@Override
	@Transactional
	public void save(ResultExam resultExam) {
		try {
			resultRepository.save(resultExam);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ResultDTO> findByUserIdDTO(Long id) {
		List<ResultExam> resultExams = findByUserId(id);
		return resultExams.isEmpty() ? null : resultExams.stream()
				.map(x -> new ResultDTO(examService.getExam(x.getExamId()).getName() ,x.getMark(), x.getWhen()))
				.toList();
	}

	@Override
	public List<Assigment> findByUsernameAssignDTO(String name) {
		UserDTO userDTO = userService.findByUsernameDTO(name);
		List<Assigment> assigments = null; 
		try {
			assigments = assigmentRepository.findByUserId(userDTO.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assigments;
	}

	@Override
	public void checkAndSaveResult(ExamPassDTO exam) {
		Long correctQuestions = 0L;
		Long ammountOfQuestions = (long) exam.getQuestions().size();
		for (Question question : exam.getQuestions()) {
			correctQuestions++;
			for (Answer answer : question.getAnswers()) {
				if (answer.isCorrect() != answerRepository.getById(answer.getId()).isCorrect()) {
					correctQuestions--;
					break;
				}
			}
		}
		ResultExam result = new ResultExam();
		result.setExamId(exam.getExamId());
		result.setUserId(exam.getUserId());
		result.setMark(correctQuestions * 100 / ammountOfQuestions);
		try {
			assigmentRepository.delete(exam.getUserId(), exam.getExamId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.save(result);
	}

	@Override
	public ExamPassDTO getExamPassDTO(Long id, String name) {
		List<Assigment> assigments = null;
		try {
			assigments = assigmentRepository.findByExamId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User user = userService.findByUsername(name);
		boolean flag = false;
		for (Assigment x : assigments) {
			if (x.getUserId() == user.getId())
				flag = true;
		}
		if (!flag)
			return null;
		Exam exam = examService.getExam(id);
		ExamPassDTO examPass = new ExamPassDTO(exam);
		examPass.setUserId(user.getId());
		if (exam.isShuffle()) {
			examPass.getQuestions().stream()
				.forEach(x -> new ListShuffleUtil<Answer>().shuffle(x.getAnswers()));
			new ListShuffleUtil<Question>().shuffle(examPass.getQuestions());
		}
		ReadyToPassUtil.prepare(examPass);
		return examPass;
	}

	@Override
	public List<AssignmentStudentDTO> convertTOAssignStudDTO(List<Assigment> assignments) {
		return assignments.stream()
				.map(x -> {
					AssignmentStudentDTO tmp = new AssignmentStudentDTO();
					tmp.setUserId(x.getUserId());
					tmp.setDescription(x.getDescription());
					tmp.setExam(new ExamDTO(examService.getExam(x.getExamId())));
					return tmp;
				})
				.toList();
	}

}
