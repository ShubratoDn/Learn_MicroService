package com.microservice.hotel.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.microservice.hotel.entities.Hotel;
import com.microservice.hotel.service.HotelService;
import com.microservice.hotel.config.CustomUserDetails;
import com.microservice.hotel.config.CurrentUserUtil;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Hotel addNewHotel(@RequestBody Hotel hotel) {
        return hotelService.addNewHotel(hotel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotelById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody Hotel updatedHotel) {
        return hotelService.updateHotel(id, updatedHotel);
    }

    @GetMapping("/searchByName")
    @PreAuthorize("isAuthenticated()")
    public List<Hotel> findHotelsByName(@RequestParam String name) {
        return hotelService.findHotelsByName(name);
    }

    @GetMapping("/searchByAddress")
    @PreAuthorize("isAuthenticated()")
    public List<Hotel> findHotelsByAddress(@RequestParam String address) {
        return hotelService.findHotelsByAddress(address);
    }

    // New endpoint to demonstrate current user access
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        // Create a simple response with user info
        String userInfo = String.format(
            "Current User: %s (Email: %s, Role: %s, Designation: %s, Name: %s)",
            userDetails.getUsername(),
            userDetails.getEmail(),
            userDetails.getRole(),
            userDetails.getDesignation(),
            userDetails.getName()
        );
        
        return ResponseEntity.ok(userInfo);
    }

    // Alternative endpoint using CurrentUserUtil
    @GetMapping("/me-util")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUserInfoUtil() {
        String username = CurrentUserUtil.getCurrentUsername();
        String email = CurrentUserUtil.getCurrentUserEmail();
        String role = CurrentUserUtil.getCurrentUserRole();
        String designation = CurrentUserUtil.getCurrentUserDesignation();
        String name = CurrentUserUtil.getCurrentUserName();
        
        String userInfo = String.format(
            "Current User (via Util): %s (Email: %s, Role: %s, Designation: %s, Name: %s)",
            username, email, role, designation, name
        );
        
        return ResponseEntity.ok(userInfo);
    }
}
