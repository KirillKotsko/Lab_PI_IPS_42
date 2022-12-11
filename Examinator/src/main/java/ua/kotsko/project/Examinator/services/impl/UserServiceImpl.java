package ua.kotsko.project.Examinator.services.impl;


import java.util.HashSet;
import java.util.Set;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.kotsko.project.Examinator.dto.UserDTO;
import ua.kotsko.project.Examinator.models.Role;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.repository.RoleRepository;
import ua.kotsko.project.Examinator.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private PasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional
	public UserDTO findByUsernameDTO(String username) {
		return new UserDTO(userRepository.findByUsername(username));
	}
	
	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Transactional
	public Role getRoleByID(Long id) {
		return roleRepository.findById(id).get(); 
	}
	
	@Transactional
	public void saveUser(User user) {
		if (user.getId() == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setActive(true);
			Set<Role> roles = new HashSet<>();
			roles.add(getRoleByID(1L));
			user.setRoles(roles);
		}
		userRepository.save(user);
	}

}
