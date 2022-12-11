package ua.kotsko.project.Examinator.services.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.kotsko.project.Examinator.models.Role;
import ua.kotsko.project.Examinator.models.User;
import ua.kotsko.project.Examinator.repository.RoleRepository;
import ua.kotsko.project.Examinator.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
				.map(x -> new SimpleGrantedAuthority(x.getName()))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) {
			user = findByEmail(username);
			if (user == null)
				throw new UsernameNotFoundException(String.format("User '%s' not found", username));
		}
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), 
						user.getPassword(), user.getActive(), user.getActive(), user.getActive(), user.getActive(),
						mapRolesToAuthorities(user.getRoles()));
		return userDetails;
	}
	
	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
