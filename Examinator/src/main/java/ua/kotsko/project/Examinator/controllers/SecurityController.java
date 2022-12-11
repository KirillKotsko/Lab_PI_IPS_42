package ua.kotsko.project.Examinator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ua.kotsko.project.Examinator.dto.PostDTO;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.services.PostService;
import ua.kotsko.project.Examinator.services.impl.UserServiceImpl;
import ua.kotsko.project.Examinator.validation.UserValidation;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class SecurityController {

	@Autowired
    private UserServiceImpl userService;
	
	@Autowired
    private UserValidation userValidator;

    @Autowired
    private PostService postService;
	
	@GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.saveUser(userForm);

        return "redirect:/login";
    }
	
	@GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        List<PostDTO> postDTOList = postService.getAllPostForUser(currentUser, new Date(System.currentTimeMillis()));
        model.addAttribute("posts", postDTOList);
		return "home";
	}
	
}

