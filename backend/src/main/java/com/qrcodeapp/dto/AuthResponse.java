package com.qrcodeapp.dto;

// Auth Response DTO
public class AuthResponse {
    public String token;
    public String fullName;
    public String role;
    public String message;
    public String subscriptionType;
    
    public AuthResponse() {}
    
    public AuthResponse(String token, String fullName, String role, String message, String subscriptionType) {
        this.token = token;
        this.fullName = fullName;
        this.role = role;
        this.message = message;
        this.subscriptionType = subscriptionType;
    }
    
    public AuthResponse(String message) {
        this.message = message;
    }
}