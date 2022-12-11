package ua.kotsko.project.Examinator.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.kotsko.project.Examinator.dto.AssignmentDTO;
import ua.kotsko.project.Examinator.dto.ExamDTO;
import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Assigment;
import ua.kotsko.project.Examinator.models.Role;
import ua.kotsko.project.Examinator.repository.AssigmentRepository;
import ua.kotsko.project.Examinator.repository.ExamRepository;
import ua.kotsko.project.Examinator.repository.RoleRepository;
import ua.kotsko.project.Examinator.repository.UserRepository;
import ua.kotsko.project.Examinator.services.TeacherService;

@Service
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AssigmentRepository dao;
	
	@Autowired
	ExamRepository examRepository;
	
	@Override
	@Transactional
	public List<UserDTO> listOfUsers() {
		List <UserDTO> resultList = userRepository.findAll().stream()
		.filter(x -> x.getRoles().contains(getRoleById(1L)))
		.map(x -> new UserDTO(x))
		.collect(Collectors.toList());
		return resultList;
	}
	
	@Transactional
	public Role getRoleById(long id) {
		return roleRepository.findById(id).get();
	}

	@Override
	@Transactional
	public List<Role> listOFRoles() {
		return roleRepository.findAll();
	}

	@Override
	public UserDTO getUserDTOById(Long id) {
		UserDTO user = new UserDTO(userRepository.findById(id).get());
		return user;
	}

	@Override
	@Transactional
	public List<UserDTO> listOfUsers(String query) {
		List <UserDTO> resultList = userRepository.findAll().stream()
				.filter(x -> x.getRoles().contains(getRoleById(1L)) &&
						(x.getUsername().toLowerCase().contains(query.toLowerCase())
						||	x.getEmail().toLowerCase().contains(query.toLowerCase())
						|| ((Role)x.getRoles().toArray()[0]).getName().toLowerCase().contains(query.toLowerCase()))
						)
				.map(x -> new UserDTO(x))
				.collect(Collectors.toList());
				return resultList;
	}

	@Override
	public List<ExamDTO> getExamDTONoUID(Long id) {
		List<Assigment> assigments = null;
		List<Long> examIds = new ArrayList<>();
		try {
			assigments = dao.findByUserId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (assigments != null)
			assigments.stream().forEach(x -> examIds.add(x.getExamId()));
		else
			return examRepository.findAll().stream()
					.map(x -> new ExamDTO(x))
					.toList();
		return examRepository.findAll().stream()
				.filter(x -> !examIds.contains(x.getId()))
				.map(x -> new ExamDTO(x))
				.toList();
	}

	@Override
	public void save(AssignmentDTO dto) {
		dto.getExams().stream().forEach(x -> {
			if (x.getChoose()) {
				Assigment assigment = new Assigment();
				assigment.setDescription(dto.getDescription());
				assigment.setUserId(dto.getUserId());
				assigment.setExamId(x.getId());
				try {
					dao.save(assigment);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

}
