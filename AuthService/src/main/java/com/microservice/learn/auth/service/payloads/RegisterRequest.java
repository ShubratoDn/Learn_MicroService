package com.microservice.learn.auth.service.payloads;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

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

    @JsonProperty
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @JsonIgnore
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonIgnore
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}