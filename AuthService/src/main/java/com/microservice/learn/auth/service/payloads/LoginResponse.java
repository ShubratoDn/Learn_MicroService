package com.microservice.learn.auth.service.payloads;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

	private String token;
	
}