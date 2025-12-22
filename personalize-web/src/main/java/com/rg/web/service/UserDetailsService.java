/**
 * 
 */
package com.rg.web.service;

import com.rg.web.dto.UserLoginRequest;
import com.rg.web.dto.UserLoginResponse;
import com.rg.web.dto.UserRegistrationRequest;
import com.rg.web.entity.User;

/**
 * @author gorle
 */
public interface UserDetailsService {

	UserLoginResponse loginValidation(UserLoginRequest request);
	
	User registerUser(UserRegistrationRequest request);
	
	boolean userExists(String username);
}
