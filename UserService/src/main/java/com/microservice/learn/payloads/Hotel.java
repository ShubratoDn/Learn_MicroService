package com.microservice.learn.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

	private Long id;
	
	private String name;
	
	private String address;
	
	private String about; 
	
}
