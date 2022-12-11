package ua.kotsko.project.Examinator.services;

import java.util.List;

import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Role;

public interface AdminService {

	public List<UserDTO> listOfUsers();
	
	public List<Role> listOFRoles();

	public UserDTO getUserDTOById(Long id);

	public List<UserDTO> listOfUsers(String query);

	public void update(UserDTO user);
}
