package ua.kotsko.project.Examinator.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.services.impl.UserServiceImpl;

@Component
public class UserValidation implements Validator {

	@Autowired
    private UserServiceImpl userService;
	
	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (user.getUsername().length() < 5 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("password", "Duplicate.userForm.email");
        }
        if (!checkEmail(user.getEmail())) {
        	errors.rejectValue("email", "Invalid.email");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "Required");
        
	}
	
	private boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	
}
