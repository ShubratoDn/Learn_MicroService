package com.microservice.learn.auth.service.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String username;
	private String password;
}