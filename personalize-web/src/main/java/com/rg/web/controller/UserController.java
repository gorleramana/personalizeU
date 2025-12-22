package com.rg.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rg.web.dto.UserLoginRequest;
import com.rg.web.dto.UserLoginResponse;
import com.rg.web.dto.UserRegistrationRequest;
import com.rg.web.dto.UserRegistrationResponse;
import com.rg.web.entity.User;
import com.rg.web.exception.ConflictException;
import com.rg.web.exception.ServiceUnavailableException;
import com.rg.web.service.UserDetailsService;
import com.rg.web.util.PersonalizeConstants;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@RestController
@Slf4j
//@RequestMapping("/rg/api")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @GetMapping(value = PersonalizeConstants.HEALTH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> health() {
        log.info("Health check endpoint called");
        return new ResponseEntity<>("Application is running", HttpStatus.OK);
    }

    @PostMapping(value = PersonalizeConstants.REGISTER_USER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("User registration/update request received for username: {}", request.getUsername());
        
        try {
            User user = userDetailsService.registerUser(request);
            boolean isUpdate = user.getId() != null && userDetailsService.userExists(request.getUsername());
            
            if (isUpdate) {
                log.info("User updated successfully with ID: {}", user.getId());
                UserRegistrationResponse resp = new UserRegistrationResponse("User updated successfully", user.getId(), true);
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                log.info("User created successfully with ID: {}", user.getId());
                UserRegistrationResponse resp = new UserRegistrationResponse("User created successfully", user.getId(), false);
                return new ResponseEntity<>(resp, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            log.error("Error during user registration/update for username: {}", request.getUsername(), e);
            throw e;
        }
    }

    @PostMapping(value = PersonalizeConstants.VALIDATE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserLoginResponse> validateLogin(@Valid @RequestBody UserLoginRequest request) {
		log.info("Login validation request received for username: {}", request.getUsername());
		try {
			UserLoginResponse result = userDetailsService.loginValidation(request);
			log.info("Login validation completed for username: {}, result: {}", request.getUsername(), result);
			
			// Return 200 OK for successful validation
			if (result != null) {
				return new ResponseEntity<UserLoginResponse>(result, HttpStatus.OK);
			} else {
				// Return 203 Non-Authoritative Information for partial success
				return new ResponseEntity<UserLoginResponse>(new UserLoginResponse("Login validation completed with warnings", null, null, null, null, null, null, false), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}
		} catch (Exception e) {
			log.error("Error during login validation for username: {}", request.getUsername(), e);
			throw e;
		}
	}
}