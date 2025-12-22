/**
 * 
 */
package com.rg.web.exception;

/**
 * @author gorle
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
