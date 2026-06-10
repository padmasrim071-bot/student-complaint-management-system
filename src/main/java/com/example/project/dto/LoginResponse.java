package com.example.project.dto;

public class LoginResponse {

    private String message;
    private String role;
    private Long userId;
    private String token;

    public LoginResponse() {}

    public LoginResponse(String message, String role, Long userId, String token) {
        this.message = message;
        this.role = role;
        this.userId = userId;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}