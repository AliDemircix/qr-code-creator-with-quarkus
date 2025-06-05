package com.qrcodeapp.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    public String email; // must match Keycloak email
    public String fullName;
    public LocalDateTime registerTime;
    public String subscriptionType;
    public int qrCodeLimit;
}