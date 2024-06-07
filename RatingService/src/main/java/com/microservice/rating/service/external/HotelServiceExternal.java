package com.microservice.rating.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.rating.payload.Hotel;

@FeignClient("HOTELSERVICE")
public interface HotelServiceExternal {
	
	 @GetMapping("/api/hotels/{id}")
	 public Hotel getHotelById(@PathVariable Long id) ;
}
