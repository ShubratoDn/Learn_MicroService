package com.microservice.hotel.serviceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.hotel.entities.Hotel;
import com.microservice.hotel.exception.ResourceNotFoundException;
import com.microservice.hotel.repository.HotelRepository;
import com.microservice.hotel.service.HotelService;
@Service
public class HotelServiceImple implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel addNewHotel(Hotel hotel) {		
		return hotelRepository.save(hotel);
	}

	@Override
	public Hotel getHotelById(Long id) {
		Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with id: "+ id));
		return hotel;
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public void deleteHotelById(Long id) {
		hotelRepository.deleteById(id);
	}

	@Override
	public Hotel updateHotel(Long id, Hotel updatedHotel) {
		Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
		hotel.setName(updatedHotel.getName());
		hotel.setAddress(updatedHotel.getAddress());
		hotel.setAbout(updatedHotel.getAbout());
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> findHotelsByName(String name) {
		return hotelRepository.findByName(name);
	}

	@Override
	public List<Hotel> findHotelsByAddress(String address) {
		return hotelRepository.findByAddress(address);
	}
}
