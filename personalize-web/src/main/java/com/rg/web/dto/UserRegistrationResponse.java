package com.rg.web.dto;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private String message;
    private Long userId;
    private boolean updated;

    public UserRegistrationResponse(String message, Long userId, boolean updated) {
        this.message = message;
        this.userId = userId;
        this.updated = updated;
    }
}
