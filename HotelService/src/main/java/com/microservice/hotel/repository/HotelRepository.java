package com.microservice.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.hotel.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
	List<Hotel> findByName(String name);
    List<Hotel> findByAddress(String address);
}
