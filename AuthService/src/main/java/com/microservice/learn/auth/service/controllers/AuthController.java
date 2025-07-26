package com.microservice.learn.auth.service.controllers;

import com.microservice.learn.auth.service.configs.jwt.JwtTokenUtil;
import com.microservice.learn.auth.service.configs.security.CustomUserDetailsServiceImpl;
import com.microservice.learn.auth.service.entities.User;
import com.microservice.learn.auth.service.payloads.AuthenticationRequest;
import com.microservice.learn.auth.service.payloads.AuthenticationResponse;
import com.microservice.learn.auth.service.payloads.ErrorResponse;
import com.microservice.learn.auth.service.payloads.RegisterRequest;
import com.microservice.learn.auth.service.repositories.UserRepository;
import com.microservice.learn.auth.service.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtTokenUtil jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;


    @GetMapping("/")
    public String login() {
        return "Welcome to Auth Service";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (BadCredentialsException e) {
            // Handle bad credentials
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "Incorrect password."
            );
            log.error("Login failed for user '{}': Incorrect username or password.", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (LockedException e) {
            // Handle locked account
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.LOCKED.value(),
                    "User Account Locked",
                    "Your account is locked. Please contact support."
            );
            log.error("Login failed for user '{}': Account is locked.", username);
            return ResponseEntity.status(HttpStatus.LOCKED).body(errorResponse);
        } catch (DisabledException e) {
            // Handle disabled account
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.FORBIDDEN.value(),
                    "User Account Disabled",
                    "Your account is disabled. Please contact support."
            );
            log.error("Login failed for user '{}': User account is disabled.", username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } catch (AccountExpiredException e) {
            // Handle expired account
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.UNAUTHORIZED.value(),
                    "User Account Expired",
                    "Your account has expired. Please contact support."
            );
            log.error("Login failed for user '{}': Account expired.", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }  catch (InternalAuthenticationServiceException e) {
            // Handle user not found
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "User Not Found",
                    "User not found."
            );
            log.error("Login failed for user '{}': User not found.", username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(request.getUsername());
        var jwtToken = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User byUsernameOrEmail = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (byUsernameOrEmail != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .error("Invalid username or email")
                    .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .message("Username or email already exist")
                    .build();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }
        User register = authenticationService.register(request);
        return ResponseEntity.ok(register);
    }

}
