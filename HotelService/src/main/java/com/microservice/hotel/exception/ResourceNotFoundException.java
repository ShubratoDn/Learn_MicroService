package com.microservice.hotel.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super("Resource Not Found!!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	
}
