package ua.kotsko.project.Examinator.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.kotsko.project.Examinator.dto.ResultDTO;
import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.services.StudentService;
import ua.kotsko.project.Examinator.services.impl.UserServiceImpl;
import ua.kotsko.project.Examinator.utils.AverageMarkUtil;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private StudentService studentService; 
	
	@GetMapping()
	public String profile(Model model, Principal principal) {
		UserDTO userDTO = userService.findByUsernameDTO(principal.getName());
		List<ResultDTO> results = studentService.findByUserIdDTO(userDTO.getId());
		Long averageMark = AverageMarkUtil.calculate(results);
		model.addAttribute("averageMark", averageMark);
		model.addAttribute("results", results);
		model.addAttribute("user", userDTO);
		return "profile";
	}
}
