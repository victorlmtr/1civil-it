package com.backend.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}") // Inject the key from application.properties
    private String secretKey;
    private final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    // generate token
    public String generateToken(String email) {

        SecretKey key = JwtUtils.getSecretKey(secretKey);

        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        System.out.println("Expiration: " + expirationDate);

        return Jwts.builder()
                .setSubject(email) // Identifies the user
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration
                .signWith(SignatureAlgorithm.HS512, key) // Signature with secret key
                .compact();
    }

    // Validation and extraction of claims
    public String extractEmailFromToken(String token) {

        SecretKey key = JwtUtils.getSecretKey(secretKey);

        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Return user's email
    }
}
