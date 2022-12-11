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

import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService; 
	
	@GetMapping("/list")
	public String listOfUsers(Model model) {
		model.addAttribute("users", adminService.listOfUsers());
		return "/admin/users";
	}
	
	@RequestMapping(value = "/list/{query}", method = RequestMethod.GET)
	public String searchQuery(Model model, @PathVariable("query") String query) {
		List<UserDTO> users = adminService.listOfUsers(query);
		model.addAttribute("users", users);
	    return "/admin/users :: resultsList";
	}
	
	@RequestMapping(value = "/list/{load}/{all}", method = RequestMethod.GET)
	public String showAllUsers(Model model, @PathVariable("load") String load, @PathVariable("all") String all) {
		List<UserDTO> users = adminService.listOfUsers();
		model.addAttribute("users", users);
	    return "/admin/users :: resultsList";
	}
	
	@GetMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", adminService.getUserDTOById(id));
		model.addAttribute("roles", adminService.listOFRoles());
		return "/admin/updateUser";
	}
	
	@PostMapping()
	public String updateUser(@ModelAttribute("User") UserDTO user) {
		adminService.update(user);
		return "redirect:/admin/list";
	}

}
