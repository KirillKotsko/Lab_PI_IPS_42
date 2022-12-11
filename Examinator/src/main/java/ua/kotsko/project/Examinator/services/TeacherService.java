package ua.kotsko.project.Examinator.services;

import java.util.List;

import ua.kotsko.project.Examinator.dto.AssignmentDTO;
import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Role;

public interface TeacherService {

	public List<UserDTO> listOfUsers();
	
	public List<Role> listOFRoles();

	public UserDTO getUserDTOById(Long id);

	public List<UserDTO> listOfUsers(String query);

	public List<ExamDTO> getExamDTONoUID(Long id);

	public void save(AssignmentDTO dto);
	
}
