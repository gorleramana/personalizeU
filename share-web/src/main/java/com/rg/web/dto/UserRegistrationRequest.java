package com.rg.web.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author gorle
 */
@Data
public class UserRegistrationRequest {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private LocalDate dateOfBirth;
    
    private String address;
    
    private String phoneNumber;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}