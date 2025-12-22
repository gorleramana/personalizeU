/**
 * 
 */
package com.rg.web.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.rg.web.dto.UserLoginRequest;
import com.rg.web.dto.UserLoginResponse;
import com.rg.web.dto.UserRegistrationRequest;
import com.rg.web.entity.User;
import com.rg.web.exception.ConflictException;
import com.rg.web.exception.ResourceNotFoundException;
import com.rg.web.exception.UnauthorizedException;
import com.rg.web.repository.UserRepository;

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
	public UserLoginResponse loginValidation(UserLoginRequest request) {
		log.debug("Starting login validation for username: {}", request.getUsername());
		UserLoginResponse userResponse = null;

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> {
					log.warn("User not found with username: {}", request.getUsername());
					return new UnauthorizedException("Invalid username or password");
				});
		boolean isLogin = user.getUsername() != null && user.getUsername().equals(request.getUsername()) && user.getPassword() != null && user.getPassword().equals(request.getPassword());
		
		if(isLogin) {
			userResponse = new UserLoginResponse(
					user.getName(),
					user.getDateOfBirth(),
					user.getAddress(),
					user.getPhoneNumber(),
					user.getEmail(),
					user.getUsername(),
					user.getPassword(),
					false);
			log.debug("Login validation successful for username: {}", request.getUsername());
			return userResponse;
		} else {
			throw new UnauthorizedException("Invalid username or password");
		}
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
