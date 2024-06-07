package com.microservice.rating.service;

import java.util.List;

import com.microservice.rating.entities.Rating;

public interface RatingService {
	Rating addRating(Rating rating);

	Rating getRatingById(Long id);

	List<Rating> getAllRatings();

	void deleteRatingById(Long id);

	List<Rating> getRatingByUserId(Long userId);

	List<Rating> getRatingByHotelId(Long hotelId);
}
