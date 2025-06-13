package com.qrcodeapp.dto;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

import com.qrcodeapp.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {
    @Inject
    JwtService jwtService;
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validate input
        if (request.email == null || request.email.trim().isEmpty()) {
            return new AuthResponse("Email is required");
        }
        
        if (request.password == null || request.password.length() < 6) {
            return new AuthResponse("Password must be at least 6 characters long");
        }
        
        // Check if user already exists
        if (User.existsByEmail(request.email)) {
            return new AuthResponse("Username already exists");
        }
        
        // Determine role
        String role = "user";
        if (request.role != null && "admin".equalsIgnoreCase(request.role)) {
            role = "admin";
        }
        
        // Hash password and create user
        String hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt());
        User user = new User();
        user.email = request.email;
        user.password = hashedPassword;
        user.role = role;
        user.fullName = request.fullName;
        user.registerTime = LocalDateTime.now();
        user.subscriptionType = "free"; // Default subscription type
        user.qrCodeLimit = 3; 
        user.persist();
        
        // Generate JWT token
        String token = jwtService.generateToken(user.email, user.role);
        
        return new AuthResponse(token, user.fullName, user.email, user.role, "User registered successfully");
    }
    public AuthResponse login(LoginRequest request) {
        // Validate input
        if (request.email == null || request.password == null) {
            return new AuthResponse("Email and password are required");
        }
        
        // Find user
        User user = User.findByEmail(request.email);
        if (user == null) {
            return new AuthResponse("Invalid email or password");
        }
        
        // Check password
        if (!BCrypt.checkpw(request.password, user.password)) {
            return new AuthResponse("Invalid email or password");
        }
        
        // Generate JWT token
        String token = jwtService.generateToken(user.email, user.role);
        
        return new AuthResponse(token, user.fullName,user.role,"Login successful", user.subscriptionType);
    }
}
