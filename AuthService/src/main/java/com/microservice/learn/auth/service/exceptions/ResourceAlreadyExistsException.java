package com.microservice.learn.auth.service.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
	
	
	public ResourceAlreadyExistsException(String resourceName, String resourcevalue) {
		super("The resource '"+resourceName+"' with value "+resourcevalue+" is already exists");
	}

}