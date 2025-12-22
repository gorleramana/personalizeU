/**
 * 
 */
package com.rg.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@RestController
@Slf4j
public class DefaultErrorController implements ErrorController {

	@GetMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> handleError(HttpServletRequest request) {
		log.info("default error message will be displayed");
		return new ResponseEntity<String>("Custom Error Message", HttpStatus.NOT_FOUND);
	}

	public String getErrorPath() {
		return "/error";
	}
}
