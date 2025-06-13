package com.qrcodeapp.model;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "qrcodes")
public class QrCode extends PanacheEntity {
    public String title;
    public String content;
    public String type; // social_media, website, video, etc.
    public LocalDateTime createdAt;

    @ManyToOne
    public User user;
}
