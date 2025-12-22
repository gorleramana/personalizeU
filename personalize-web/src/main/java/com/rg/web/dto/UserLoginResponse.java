package com.rg.web.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserLoginResponse {

    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private boolean updated;

    public UserLoginResponse(String name, LocalDate dateOfBirth, String address, String phoneNumber, String email, String username, String password, boolean updated) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.updated = updated;
    }
}