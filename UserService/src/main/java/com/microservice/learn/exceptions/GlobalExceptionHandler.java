package com.microservice.learn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.microservice.learn.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		ApiResponse resp = new ApiResponse();
		resp.setMessage(ex.getMessage());
		resp.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);		
	}
	
}
