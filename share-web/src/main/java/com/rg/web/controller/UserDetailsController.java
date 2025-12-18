/**
 * 
 */
package com.rg.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rg.web.service.UserDetailsService;
import com.rg.web.util.RGConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@RestController
@Slf4j
public class UserDetailsController {

	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping(value = RGConstants.VALIDATE_USER, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> validateLogin(String username, String password, String remember) {
		log.info("Login validation request received for username: {}", username);
		try {
			String result = userDetailsService.loginValidation(username, password, remember);
			log.info("Login validation completed for username: {}, result: {}", username, result);
			
			// Return 200 OK for successful validation
			if ("Y".equals(result)) {
				return new ResponseEntity<>("Login successful", HttpStatus.OK);
			} else {
				// Return 203 Non-Authoritative Information for partial success
				return new ResponseEntity<>("Login validation completed with warnings", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}
		} catch (Exception e) {
			log.error("Error during login validation for username: {}", username, e);
			throw e;
		}
	}
}
