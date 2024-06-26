package com.microservice.learn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.learn.entities.User;
import com.microservice.learn.exceptions.ResourceNotFoundException;
import com.microservice.learn.payloads.Rating;
import com.microservice.learn.repository.UserRepository;
import com.microservice.learn.service.external.RatingServiceExternal;

@Service
public class UserServiceImple implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RatingServiceExternal ratingServiceExternal;

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

		List<Rating> ratings = ratingServiceExternal.getRatingByUserId(user.getId());

		user.setRatings(ratings);

		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllUser() {
		 List<User> findAll = userRepository.findAll();
		for(User user: findAll) {
			List<Rating> ratings = ratingServiceExternal.getRatingByUserId(user.getId());
			user.setRatings(ratings);
		}
		 return findAll;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
