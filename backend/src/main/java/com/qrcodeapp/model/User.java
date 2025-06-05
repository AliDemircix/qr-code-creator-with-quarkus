package com.qrcodeapp.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password; // hashed password

    @Column(name = "full_name")
    public String fullName;

    @Column(name = "is_admin")
    public boolean isAdmin = false;

    @Column(name = "subscription_type")
    public String subscriptionType = "free"; // free or premium

    @Column(name = "qr_code_limit")
    public int qrCodeLimit = 3; // default for free users

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}