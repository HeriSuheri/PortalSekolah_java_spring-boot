package com.example.portal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Base64;
import java.security.Key;

@Component
public class JwtUtil {

    private final String secret = "rahasia-portal-sekolah-yang-panjang-dan-aman";
    private final Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes()));

    // public String generateToken(String nomorInduk, String role) {
    // return Jwts.builder()
    // .setSubject(nomorInduk)
    // .claim("role", role)
    // .setIssuedAt(new Date())
    // .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 jam
    // // .setExpiration(new Date(System.currentTimeMillis() + 360))
    // .signWith(key, SignatureAlgorithm.HS256)
    // .compact();
    // }

    public String generateToken(String nomorInduk, String role) {
        long expirationMillis = 16 * 60 * 60 * 1000; // 16 jam
        return Jwts.builder()
                .setSubject(nomorInduk)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}