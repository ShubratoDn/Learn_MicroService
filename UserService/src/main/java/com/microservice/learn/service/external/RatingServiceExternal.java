package com.microservice.learn.service.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.learn.payloads.Rating;

@FeignClient(name = "RATINGSERVICE")
public interface RatingServiceExternal {

	@GetMapping("/api/ratings/user/{userId}")
//    public ResponseEntity<?> getRatingByUserId(@PathVariable Long userId);
	public List<Rating> getRatingByUserId(@PathVariable Long userId);
	
}
