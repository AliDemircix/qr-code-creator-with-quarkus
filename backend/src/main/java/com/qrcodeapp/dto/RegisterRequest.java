package com.qrcodeapp.dto;


public class RegisterRequest {

     public String email;
    public String fullName;
    public String password; // Store hashed password
    public String role;

    public RegisterRequest() {
    }

    public RegisterRequest(String email,String fullName, String password, String role) {
        this.fullName = fullName;
        this.email=email;
        this.password = password;
        this.role = role;
    }
}
