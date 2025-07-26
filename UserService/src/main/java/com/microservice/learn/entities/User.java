package com.microservice.learn.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.microservice.learn.payloads.Rating;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String about;
	private String username;
	private String password;
	private String address;
	private String remark;
	private String designation;
	private String image;
	private Boolean isLocked;
	private Date createdAt;
	private Date updatedAt;
	
	@Transient
	private List<Rating> ratings = new ArrayList<>();
	
}
