/**
 * 
 */
package com.rg.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.rg.web.dto.UserRegistrationRequest;
import com.rg.web.entity.User;
import com.rg.web.exception.ConflictException;
import com.rg.web.exception.ResourceNotFoundException;
import com.rg.web.exception.UnauthorizedException;
import com.rg.web.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	//@Cacheable(value = "products", key = "#id") TODO: redis cache 
	@Override
	public String loginValidation(String username, String password, String remember) {
		log.debug("Starting login validation for username: {}", username);
		
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("Password cannot be empty");
		}
		
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User not found: " + username);
		}
		
		if (!password.equals(user.get().getPassword())) {
			throw new UnauthorizedException("Invalid credentials for user: " + username);
		}
		
		log.debug("Login validation successful for username: {}", username);
		return "Y";
	}
	
	@Override
	public User registerUser(UserRegistrationRequest request) {
		log.info("Starting user registration/update for username: {}", request.getUsername());
		
		User user = userRepository.findByUsername(request.getUsername()).orElse(new User());
		boolean isUpdate = user.getId() != null;
		
		if (!isUpdate && userRepository.existsByEmail(request.getEmail())) {
			throw new ConflictException("Email already exists: " + request.getEmail());
		}
		
		user.setName(request.getName());
		user.setDateOfBirth(request.getDateOfBirth());
		user.setAddress(request.getAddress());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setEmail(request.getEmail());
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		
		User savedUser = userRepository.save(user);
		log.info("User {} successfully with ID: {}", isUpdate ? "updated" : "created", savedUser.getId());
		return savedUser;
	}
	
	@Override
	public boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}

}
