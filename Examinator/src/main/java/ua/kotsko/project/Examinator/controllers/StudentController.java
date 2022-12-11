package ua.kotsko.project.Examinator.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.kotsko.project.Examinator.dto.AssignmentDTO;
import ua.kotsko.project.Examinator.dto.AssignmentStudentDTO;
import ua.kotsko.project.Examinator.dto.ExamPassDTO;
import ua.kotsko.project.Examinator.models.Assigment;
import ua.kotsko.project.Examinator.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/assignments")
	public String assignmentStudent(Model model, Principal principal) {
		List<Assigment> assignment = studentService.findByUsernameAssignDTO(principal.getName());
		List<AssignmentStudentDTO> dto =  studentService.convertTOAssignStudDTO(assignment);
		model.addAttribute("assignments", dto);
		return "/student/assignments";
	}
	
	@GetMapping("/pass/{id}")
	public String passExam(@PathVariable("id") Long id, Model model, Principal principal) {
		ExamPassDTO exam = studentService.getExamPassDTO(id, principal.getName());
		model.addAttribute("exam", exam);
		if (exam == null)
			return "redirect:/";
		return "/student/pass";
	}
	
	@PostMapping("/")
	public String acceptExam(@ModelAttribute("exam") ExamPassDTO exam) {
		studentService.checkAndSaveResult(exam);
		return "redirect:/profile";
	}
}
