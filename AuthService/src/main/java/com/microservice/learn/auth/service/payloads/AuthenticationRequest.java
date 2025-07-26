package com.microservice.learn.auth.service.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
	private String username;
	private String password;
}