package com.microservice.learn.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.learn.config.CurrentUserUtil;
import com.microservice.learn.config.CustomUserDetails;
import com.microservice.learn.entities.User;
import com.microservice.learn.exceptions.ResourceNotFoundException;
import com.microservice.learn.payloads.ApiResponse;
import com.microservice.learn.payloads.UserInfo;
import com.microservice.learn.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
    	
    	User userByEmail = userService.getUserByEmail(user.getEmail());
    	
    	if(userByEmail != null) {
    		return new ResponseEntity<>(new ApiResponse("User with the email '"+user.getEmail()+"' already exists!",HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    	}
    	
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Get a user by ID - requires authentication
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get a user by email - requires authentication
    @GetMapping("/email/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get all users - requires ADMIN role
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Update a user - requires authentication
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            user.setId(id);
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
    }

    // Delete a user - requires ADMIN role
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
    }

    // Get current logged-in user information using Spring Security
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfo> getCurrentUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserInfo userInfo = new UserInfo(
            userDetails.getUsername(), 
            userDetails.getEmail(), 
            userDetails.getRole(), 
            userDetails.getDesignation(), 
            userDetails.getName()
        );
        
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // Get current logged-in user's full profile using Spring Security
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getCurrentUserProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getEmail();
        
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        User user = userService.getUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User profile not found for email: " + email);
        }
    }

    // Alternative method using the utility class
    @GetMapping("/me-util")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserInfo> getCurrentUserInfoUtil() {
        if (!CurrentUserUtil.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        UserInfo userInfo = new UserInfo(
            CurrentUserUtil.getCurrentUsername(),
            CurrentUserUtil.getCurrentUserEmail(),
            CurrentUserUtil.getCurrentUserRole(),
            CurrentUserUtil.getCurrentUserDesignation(),
            CurrentUserUtil.getCurrentUserName()
        );
        
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
