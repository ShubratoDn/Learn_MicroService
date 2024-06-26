package com.microservice.rating.controller;

import com.microservice.rating.entities.Rating;
import com.microservice.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public Rating addRating(@RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }
    

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRatingByUserId(@PathVariable Long userId) {
    	List<Rating> ratingByUserId = ratingService.getRatingByUserId(userId);
    	System.out.println("Rating service: SIZE IS:  "+ratingByUserId.size());
        return new ResponseEntity<>(ratingByUserId, HttpStatus.OK);
    }
    
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getRatingByHotelId(@PathVariable Long hotelId) {
        return new ResponseEntity<>(ratingService.getRatingByHotelId(hotelId), HttpStatus.OK);
    }

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @DeleteMapping("/{id}")
    public void deleteRatingById(@PathVariable Long id) {
        ratingService.deleteRatingById(id);
    }

    // Add more mappings as needed

}
