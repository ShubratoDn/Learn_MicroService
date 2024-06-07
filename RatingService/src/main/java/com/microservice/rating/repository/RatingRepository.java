package com.microservice.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.rating.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
	List<Rating> findByUserId(Long id);
	List<Rating> findByHotelId(Long id);
}
