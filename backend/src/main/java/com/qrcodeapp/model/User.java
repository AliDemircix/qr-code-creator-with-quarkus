package com.qrcodeapp.model;

import java.time.LocalDateTime;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
@UserDefinition 
public class User extends PanacheEntity {
    @Username
    public String email; 
    public String fullName;
    @Password
    public String password; // Store hashed password
    @Roles
    public String role;
    public LocalDateTime registerTime;
    public String subscriptionType;
    public int qrCodeLimit;

       public static void add(String email, String password, String role, String username) { 
        User user = new User();
        user.email = email;
        user.fullName = username; // Default full name is the username
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        user.registerTime = LocalDateTime.now();
        user.subscriptionType = "free"; // Default subscription type
        user.qrCodeLimit = 3; // Default QR code limit
        user.persist();
    }
}