package com.qrcodeapp.dto;

import java.time.Duration;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "smallrye.jwt.new-token.issuer")
    String issuer;

    @ConfigProperty(name = "smallrye.jwt.new-token.audience")
    String audience;

    public String generateToken(String email, String role) {
        return Jwt.issuer(issuer)
                .audience(audience)
                .subject(email)
                .groups(Set.of(role))
                .claim("email", email)
                .claim("role", role)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
