package com.qrcodeapp.dto;

import java.time.LocalDateTime;

import com.qrcodeapp.model.QrCode;

public class QrCodeDto {

    public Long id; // Unique identifier for the QR code
    public String title;
    public String content;
    public String type; // social_media, website, video, etc.
    public LocalDateTime createdAt;

    public QrCodeDto(Long id, String title, String content, String type, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static QrCodeDto fromEntity(QrCode qr) {
        return new QrCodeDto(
                qr.id,
                qr.title,
                qr.content,
                qr.type,
                qr.createdAt
        );
    }
}
