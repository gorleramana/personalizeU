/**
 * 
 */
package com.rg.web.exception;

/**
 * @author gorle
 */
public class ErrorResponse {
	
	private int status;
    private String error;
    private Object details;
    
    public ErrorResponse(int status, String error, Object details) {
    	this.status = status;
    	this.error = error;
    	this.details = details;
	}
}
