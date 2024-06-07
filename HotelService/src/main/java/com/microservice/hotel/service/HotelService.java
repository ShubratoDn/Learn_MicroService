package com.microservice.hotel.service;

import java.util.List;

import com.microservice.hotel.entities.Hotel;

public interface HotelService {

    Hotel addNewHotel(Hotel hotel);

    Hotel getHotelById(Long id);

    List<Hotel> getAllHotels();

    void deleteHotelById(Long id);

    Hotel updateHotel(Long id, Hotel updatedHotel);

    List<Hotel> findHotelsByName(String name);

    List<Hotel> findHotelsByAddress(String address);
}