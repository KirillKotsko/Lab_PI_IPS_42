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

import ua.kotsko.project.Examinator.dto.AssignmentDTO;
import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.dto.ResultDTO;
import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.services.StudentService;
import ua.kotsko.project.Examinator.services.TeacherService;
import ua.kotsko.project.Examinator.utils.AverageMarkUtil;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService; 
	
	@GetMapping("/list")
	public String listStudents(Model model) {
		model.addAttribute("users", teacherService.listOfUsers());
		return "/teacher/users";
	}
	
	@RequestMapping(value = "/list/{query}", method = RequestMethod.GET)
	public String searchQuery(Model model, @PathVariable("query") String query) {
		List<UserDTO> users = teacherService.listOfUsers(query);
		model.addAttribute("users", users);
	    return "/teacher/users :: resultsList";
	}
	
	@RequestMapping(value = "/list/{load}/{all}", method = RequestMethod.GET)
	public String showAllUsers(Model model, @PathVariable("load") String load, @PathVariable("all") String all) {
		List<UserDTO> users = teacherService.listOfUsers();
		model.addAttribute("users", users);
	    return "/teacher/users :: resultsList";
	}
	
	@GetMapping("/{id}")
	public String showUser(@PathVariable("id") Long id, Model model) {
		List<ResultDTO> results = studentService.findByUserIdDTO(id);
		Long averageMark = AverageMarkUtil.calculate(results);
		model.addAttribute("averageMark", averageMark);
		model.addAttribute("results", results);
		model.addAttribute("user", teacherService.getUserDTOById(id));
		return "/teacher/student";
	}
	
	@GetMapping("/assignment/new/{id}")
	public String newAssignment(@PathVariable("id") Long id, Model model) {
		UserDTO userDTO = teacherService.getUserDTOById(id);
		List<ExamDTO> examsDTO = teacherService.getExamDTONoUID(id);
		AssignmentDTO assignmentDTO = new AssignmentDTO();
		assignmentDTO.setUserId(userDTO.getId());
		assignmentDTO.setExams(examsDTO);
		model.addAttribute("dto", assignmentDTO);
		return "/teacher/new_assigment";
	}
	
	@PostMapping()
	public String makeAssignment(@ModelAttribute("dto") AssignmentDTO dto) {
		teacherService.save(dto);
		return "redirect:/teacher/list";
	}
}
