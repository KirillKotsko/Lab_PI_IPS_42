package ua.kotsko.project.Examinator.dto;

import java.util.Set;

import lombok.Data;
import ua.kotsko.project.Examinator.models.Role;
import ua.kotsko.project.Examinator.models.User;

@Data
public class UserDTO {

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean active;
	private Role role;
	
	public UserDTO() {
		
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getName();
		this.lastName = user.getSurname();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.active = user.getActive();
		this.role = (Role) user.getRoles().toArray()[0];
	}
	
	
}
