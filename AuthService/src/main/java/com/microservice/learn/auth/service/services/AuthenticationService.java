package com.microservice.learn.auth.service.services;

import com.microservice.learn.auth.service.configs.jwt.JwtTokenUtil;
import com.microservice.learn.auth.service.configs.security.CustomUserDetailsServiceImpl;
import com.microservice.learn.auth.service.entities.User;
import com.microservice.learn.auth.service.payloads.AuthenticationRequest;
import com.microservice.learn.auth.service.payloads.AuthenticationResponse;
import com.microservice.learn.auth.service.payloads.ErrorResponse;
import com.microservice.learn.auth.service.payloads.RegisterRequest;
import com.microservice.learn.auth.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .address(request.getAddress())
                .about(request.getAbout())
                .designation(request.getDesignation())
                .image(request.getImage())
                .isLocked(false)
                .createdAt(new Date())
            .build();
        User save = userRepository.save(user);

        return save;
    }

}