/**
 * 
 */
package com.rg.web.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	   @ExceptionHandler(ResourceNotFoundException.class)
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public ErrorResponse handleResourceNotFound(ResourceNotFoundException ex) {
	        log.warn("Resource not found: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), 
	                                "Resource Not Found", 
	                                ex.getMessage());
	    }

	    // 400 - Bad Request
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
	        List<String> errors = ex.getBindingResult()
	                                .getFieldErrors()
	                                .stream()
	                                .map(FieldError::getDefaultMessage)
	                                .collect(Collectors.toList());
	        log.warn("Validation error: {}", errors);
	        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
	                                "Validation Error", 
	                                errors);
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
	        log.warn("Invalid argument: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
	                                "Bad Request",
	                                ex.getMessage());
	    }

	    // 401 - Unauthorized
	    @ExceptionHandler(UnauthorizedException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    public ErrorResponse handleUnauthorized(UnauthorizedException ex) {
	        log.warn("Unauthorized access: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
	                                "Unauthorized",
	                                ex.getMessage());
	    }

	    // 403 - Forbidden
	    @ExceptionHandler(AccessDeniedException.class)
	    @ResponseStatus(HttpStatus.FORBIDDEN)
	    public ErrorResponse handleAccessDenied(AccessDeniedException ex) {
	        log.warn("Access denied: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
	                                "Forbidden",
	                                "Access denied to this resource");
	    }

	    @ExceptionHandler(ForbiddenException.class)
	    @ResponseStatus(HttpStatus.FORBIDDEN)
	    public ErrorResponse handleForbidden(ForbiddenException ex) {
	        log.warn("Forbidden access: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.FORBIDDEN.value(),
	                                "Forbidden",
	                                ex.getMessage());
	    }

	    @ExceptionHandler(Exception.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public ErrorResponse handleAllExceptions(Exception ex) {
	        log.error("Unexpected error occurred", ex);
	        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
	                                "Internal Server Error", 
	                                "An unexpected error occurred");
	    }
	    
	    // 406 - Not Acceptable
	    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	    public ErrorResponse handleMediaTypeException(HttpMediaTypeNotAcceptableException ex) {
	        log.warn("Media type not acceptable: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),
	                                "Not Acceptable",
	                                "No acceptable media types found");
	    }

	    // 409 - Conflict
	    @ExceptionHandler(ConflictException.class)
	    @ResponseStatus(HttpStatus.CONFLICT)
	    public ErrorResponse handleConflict(ConflictException ex) {
	        log.warn("Resource conflict: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.CONFLICT.value(),
	                                "Conflict",
	                                ex.getMessage());
	    }

	    // 500 - Internal Server Error
	    @ExceptionHandler(DataAccessException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    public ErrorResponse handleDataAccessException(DataAccessException ex) {
	        log.error("Database error occurred", ex);
	        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                                "Internal Server Error",
	                                "Database operation failed");
	    }

	    // 503 - Service Unavailable
	    @ExceptionHandler(ServiceUnavailableException.class)
	    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	    public ErrorResponse handleServiceUnavailable(ServiceUnavailableException ex) {
	        log.error("Service unavailable: {}", ex.getMessage());
	        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
	                                "Service Unavailable",
	                                ex.getMessage());
	    }
	}
