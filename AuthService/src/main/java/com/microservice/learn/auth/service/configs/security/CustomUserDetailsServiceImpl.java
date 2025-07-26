package com.microservice.learn.auth.service.configs.security;



import com.microservice.learn.auth.service.entities.User;
import com.microservice.learn.auth.service.exceptions.ResourceNotFoundException;
import com.microservice.learn.auth.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsernameOrEmail(username, username);
		if(user == null) {
			throw new ResourceNotFoundException("User name not found");
		}
		
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);		
		return customUserDetails;
	}

}