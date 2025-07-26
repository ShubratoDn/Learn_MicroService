package com.microservice.learn.auth.service.repositories;

import com.microservice.learn.auth.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String username, String email);
}
