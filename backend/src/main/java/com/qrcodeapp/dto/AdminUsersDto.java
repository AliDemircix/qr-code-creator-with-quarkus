package com.qrcodeapp.dto;

import java.time.LocalDateTime;

import com.qrcodeapp.model.User;

public class AdminUsersDto {

    public String fullName;
    public String email;
    public String role;
    public String subscriptionType;
    public LocalDateTime registerTime;
    public int qrCodeLimit;

    public AdminUsersDto(String fullName, String email, String role, String subscriptionType, LocalDateTime registerTime, int qrCodeLimit) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.subscriptionType = subscriptionType;
        this.registerTime = registerTime;
        this.qrCodeLimit = qrCodeLimit;
    }

    public static AdminUsersDto fromUser(User user) {
        return new AdminUsersDto(user.fullName, user.email, user.role,
                user.subscriptionType, user.registerTime, user.qrCodeLimit);
    }
}
