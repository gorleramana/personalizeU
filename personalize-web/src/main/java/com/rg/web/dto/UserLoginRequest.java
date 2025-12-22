package com.rg.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author gorle
 */
@Data
public class UserLoginRequest {
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Password is required")
    private String password;
}