package com.microservice.learn.api.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @GetMapping("/")
    public String home() {
        return "API Gateway is running!";
    }

    @GetMapping("/health")
    public String health() {
        return "API Gateway is healthy!";
    }
} 