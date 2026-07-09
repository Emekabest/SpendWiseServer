package org.example.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;



@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long ACCESSTOKEN_JWTEXPIRATION = 1000 * 60 * 10;
    private static final long REFRESHTOKEN_JWTEXPIRATION = 1000 * 60 * 60;


    private Key getSignKey() {

        return Keys.hmacShaKeyFor(secret.getBytes());

    }
    public String generateAccessToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESSTOKEN_JWTEXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESHTOKEN_JWTEXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUsername(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public Boolean isTokenValid(String token, User userDetails){

        final String userEmail = extractUsername(token);

        return userEmail.equals(userDetails.getEmail())
                && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){

        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

}