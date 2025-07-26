package com.microservice.learn.auth.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String resourceName, String resouceValue) {
		super(resourceName + " not found for value "+ resouceValue);
	}
}