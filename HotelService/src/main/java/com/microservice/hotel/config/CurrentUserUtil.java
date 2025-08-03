package com.microservice.hotel.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtil {

    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUsername() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    public static String getCurrentUserEmail() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getEmail() : null;
    }

    public static String getCurrentUserRole() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getRole() : null;
    }

    public static String getCurrentUserDesignation() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getDesignation() : null;
    }

    public static String getCurrentUserName() {
        CustomUserDetails user = getCurrentUser();
        return user != null ? user.getName() : null;
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
} 