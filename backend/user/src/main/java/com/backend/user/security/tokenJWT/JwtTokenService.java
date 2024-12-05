package com.backend.user.security.tokenJWT;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
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
                .setExpiration(expirationDate) // Expiration
                .signWith(SignatureAlgorithm.HS256, key) // Shorter signature
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

    public boolean validateToken(String token) {

        try {
            SecretKey key = JwtUtils.getSecretKey(secretKey);
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token); // throws an exception if the token is invalid
            return true;

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {

            return false; // Token is invalid or expired
        }
    }

    public String extractToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

}
