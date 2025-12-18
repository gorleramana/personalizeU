/**
 * 
 */
package com.rg.web.service;

import com.rg.web.dto.UserRegistrationRequest;
import com.rg.web.entity.User;

/**
 * @author gorle
 */
public interface UserDetailsService {

	String loginValidation(String username, String password, String remember);
	
	User registerUser(UserRegistrationRequest request);
	
	boolean userExists(String username);
}
