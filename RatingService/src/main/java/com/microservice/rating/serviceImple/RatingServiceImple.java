package com.microservice.rating.serviceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.rating.entities.Rating;
import com.microservice.rating.repository.RatingRepository;
import com.microservice.rating.service.RatingService;
import com.microservice.rating.service.external.HotelServiceExternal;

@Service
public class RatingServiceImple implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private HotelServiceExternal hotelServiceExternal;
	
	@Override
    public Rating addRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public void deleteRatingById(Long id) {
        ratingRepository.deleteById(id);
    }
    
    @Override
    public List<Rating> getRatingByUserId(Long userId) {
    	List<Rating> findByUserId = ratingRepository.findByUserId(userId);
    	
    	for(Rating rating: findByUserId) {
    		rating.setHotel(hotelServiceExternal.getHotelById(rating.getHotelId()));
    	}
    	return findByUserId;
    }
    
    @Override
    public List<Rating> getRatingByHotelId(Long hotelId) {
    	List<Rating> findByHotelId = ratingRepository.findByHotelId(hotelId);
    	return findByHotelId;
    }


	
}
