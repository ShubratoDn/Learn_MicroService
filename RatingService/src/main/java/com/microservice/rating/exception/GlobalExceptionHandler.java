package com.microservice.rating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.rating.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ApiResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
	
}
