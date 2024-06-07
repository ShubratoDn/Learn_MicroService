package com.microservice.hotel.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.microservice.hotel.entities.Hotel;
import com.microservice.hotel.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public Hotel addNewHotel(@RequestBody Hotel hotel) {
        return hotelService.addNewHotel(hotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @DeleteMapping("/{id}")
    public void deleteHotelById(@PathVariable Long id) {
        hotelService.deleteHotelById(id);
    }

    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable Long id, @RequestBody Hotel updatedHotel) {
        return hotelService.updateHotel(id, updatedHotel);
    }

    @GetMapping("/searchByName")
    public List<Hotel> findHotelsByName(@RequestParam String name) {
        return hotelService.findHotelsByName(name);
    }

    @GetMapping("/searchByAddress")
    public List<Hotel> findHotelsByAddress(@RequestParam String address) {
        return hotelService.findHotelsByAddress(address);
    }
}
