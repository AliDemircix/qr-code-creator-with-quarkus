package com.qrcodeapp.dto;

import java.time.LocalDateTime;


public class UserDto {
    public Long id;
    public String email;
    public String fullName;
    public String role;
    public LocalDateTime registerTime;
    public String subscriptionType;
    public int qrCodeLimit;

    public UserDto(Long id, String email, String fullName, String role, LocalDateTime registerTime, String subscriptionType, int qrCodeLimit) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.registerTime = registerTime;
        this.subscriptionType = subscriptionType;
        this.qrCodeLimit = qrCodeLimit;
    }

    public static UserDto fromEntity(com.qrcodeapp.model.User user) {
        return new UserDto(
            user.id,
            user.email,
            user.fullName,
            user.role,
            user.registerTime,
            user.subscriptionType,
            user.qrCodeLimit
        );
    }
} 