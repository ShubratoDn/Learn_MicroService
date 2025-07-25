package com.microservice.learn.auth.service.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    private String role;
    private String password;
    private String address;
    private String about;
    private String designation;
    private String image;
    private Boolean isLocked;
    private Date createdAt;
    private Date updatedAt;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<UserRole> roles = new ArrayList<>();
    
}