package com.microservice.learn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.learn.entities.User;
import com.microservice.learn.exceptions.ResourceNotFoundException;
import com.microservice.learn.payloads.Rating;
import com.microservice.learn.repository.UserRepository;
import com.microservice.learn.service.external.RatingServiceExternal;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

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
	@CircuitBreaker(name = "ratingServiceCB", fallbackMethod = "getUserByIdFallback")
	@Retry(name = "ratingServiceRetry", fallbackMethod = "getUserByIdFallback")
	public User getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));

		List<Rating> ratings = ratingServiceExternal.getRatingByUserId(user.getId());
		user.setRatings(ratings);
		return user;
	}

	public User getUserByIdFallback(Long id, Throwable t) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id : " + id));
		user.setRatings(List.of());
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@CircuitBreaker(name = "ratingServiceCB", fallbackMethod = "getAllUserFallback")
	@Retry(name = "ratingServiceRetry", fallbackMethod = "getAllUserFallback")
	public List<User> getAllUser() {
		List<User> findAll = userRepository.findAll();
		for (User user : findAll) {
			List<Rating> ratings = ratingServiceExternal.getRatingByUserId(user.getId());
			user.setRatings(ratings);
		}
		return findAll;
	}

	public List<User> getAllUserFallback(Throwable t) {
		List<User> findAll = userRepository.findAll();
		for (User user : findAll) {
			user.setRatings(List.of());
		}
		return findAll;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
