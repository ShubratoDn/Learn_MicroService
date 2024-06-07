package com.microservice.learn.service;

import java.util.List;

import com.microservice.learn.entities.User;

public interface UserService {

	public User saveUser(User user);
	
	public User getUserById(Long id);

	public User getUserByEmail(String email);
	
	public List<User> getAllUser();

	public void deleteUser(Long id);
	
}
