package com.bookcharm.app.dto;

public class AuthenticationResponse {
    private Long userId;

    // Default constructor (required for JSON deserialization)
    public AuthenticationResponse() {
    }

    // Constructor with userId
    public AuthenticationResponse(Long userId) {
        this.userId = userId;
    }

    // Getter and setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
