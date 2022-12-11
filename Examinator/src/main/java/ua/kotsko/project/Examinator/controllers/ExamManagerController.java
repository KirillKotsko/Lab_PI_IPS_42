package ua.kotsko.project.Examinator.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.models.Answer;
import ua.kotsko.project.Examinator.models.Exam;
import ua.kotsko.project.Examinator.services.ExamManagerService;

@Controller
@RequestMapping("/exam")
public class ExamManagerController {
	
	@Autowired
	private ExamManagerService service;
	
	@GetMapping()
	public String index(Model model) {
		List<ExamDTO> exams = service.getExamsDTO();
		model.addAttribute("exams", exams);
		return "/teacher/exam_list";
	}
	
	@RequestMapping(value = "/sort/{query}", method = RequestMethod.GET)
	public String sortQuery(Model model, @PathVariable("query") String query) {
		List<ExamDTO> exams = service.getExamsDTO(query.substring(0, 1), query.substring(1));
		model.addAttribute("exams", exams);
	    return "/teacher/exam_list :: resultsList";
	}
	
	@RequestMapping(value = "/list/{query}", method = RequestMethod.GET)
	public String searchQuery(Model model, @PathVariable("query") String query) {
		List<ExamDTO> exams = service.getExamsDTO(query);
		model.addAttribute("exams", exams);
	    return "/teacher/exam_list :: resultsList";
	}
	
	@RequestMapping(value = "/list/{load}/{all}", method = RequestMethod.GET)
	public String showAllUsers(Model model, @PathVariable("load") String load, @PathVariable("all") String all) {
		List<ExamDTO> exams = service.getExamsDTO();
		model.addAttribute("exams", exams);
	    return "/teacher/exam_list :: resultsList";
	}
	
	@GetMapping("/new")
	public String newExam(@ModelAttribute("exam") Exam exam) {
		exam.addQuestionAndAnswerPair();
		return "/teacher/create_exam";
	}
	
	@GetMapping("/update/{id}")
	public String updateExam(@PathVariable("id") Long id, Model model) {
		Exam exam = service.getExam(id);
        model.addAttribute("exam", exam);
        return "/teacher/update_exam";
	}
	
	@GetMapping("/delete/{id}")
    public String deleteExam(@PathVariable (value = "id") long id) {
  
        service.deleteExam(id);
        return "redirect:/exam";
    }
	
	@PostMapping(params = "save")
	public String create(@ModelAttribute("exam") Exam exam) {
		exam.updateEntityOnQuestions();
		service.saveExam(exam);
		return "redirect:/exam";
	}
	
	@PostMapping(params = "addQuestion")
	public String addQuestion(@ModelAttribute("exam") Exam exam) {
		exam.addQuestionAndAnswerPair();
		if (exam.getId() != null)
			return "/teacher/update_exam";
		else
			return "/teacher/create_exam";
	}
	
	@PostMapping(params = "removeQuestion")
	public String removeQuestion(@ModelAttribute("exam") Exam exam, @RequestParam("removeQuestion") String index) {
		exam.getQuestions().remove(Integer.parseInt(index));
		if (exam.getId() != null)
			return "/teacher/update_exam";
		else
			return "/teacher/create_exam";
	}
	
	@PostMapping(params = "addAnswer")
	public String addAnswer(@ModelAttribute("exam") Exam exam, @RequestParam("addAnswer") String index) {
		exam.getQuestions().get(Integer.valueOf(index)).addAnswer(new Answer());
		if (exam.getId() != null)
			return "/teacher/update_exam";
		else
			return "/teacher/create_exam";
	}
	
	@PostMapping(params = "removeAnswer")
	public String removeAnswer(@ModelAttribute("exam") Exam exam, @RequestParam("removeAnswer") String values) {
		String[] indexs = values.split("_");
		exam.getQuestions().get(Integer.parseInt(indexs[0]))
			.getAnswers().remove(Integer.parseInt(indexs[1]));
		if (exam.getId() != null)
			return "/teacher/update_exam";
		else
			return "/teacher/create_exam";
	}
}
