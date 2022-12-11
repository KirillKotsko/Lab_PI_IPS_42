package ua.kotsko.project.Examinator.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Role;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.repository.RoleRepository;
import ua.kotsko.project.Examinator.repository.UserRepository;
import ua.kotsko.project.Examinator.services.AdminService;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	@Transactional
	public List<UserDTO> listOfUsers() {
		List <UserDTO> resultList = userRepository.findAll().stream()
		.filter(x -> !x.getRoles().contains(getRoleById(3L)))
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
	@Transactional
	public UserDTO getUserDTOById(Long id) {
		UserDTO user = new UserDTO(userRepository.findById(id).get());
		return user;
	}

	@Override
	@Transactional
	public List<UserDTO> listOfUsers(String query) {
		List <UserDTO> resultList = userRepository.findAll().stream()
				.filter(x -> !x.getRoles().contains(getRoleById(3L)) &&
						(x.getUsername().toLowerCase().contains(query.toLowerCase())
						||	x.getEmail().toLowerCase().contains(query.toLowerCase())
						|| ((Role)x.getRoles().toArray()[0]).getName().toLowerCase().contains(query.toLowerCase()))
						)
				.map(x -> new UserDTO(x))
				.collect(Collectors.toList());
				return resultList;
	}

	@Override
	@Transactional
	public void update(UserDTO user) {
		user.setRole(getRoleById(user.getRole().getId()));
		User updateUser = userRepository.findById(user.getId()).get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(user.getRole());
		updateUser.setRoles(roles);
		updateUser.setActive(user.getActive() == null ? false : true);
		userRepository.save(updateUser);
	}

}
