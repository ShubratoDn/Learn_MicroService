package com.microservice.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.learn.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

	User findByEmail(String email);
	
}
