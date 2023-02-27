package com.defers.mypastebin.security;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Data
@Component
public class JWTServiceImpl implements JWTService {

    private String secretKey;
    private long expirationSeconds;
    private JwtParser jwtBuilder;

    JWTServiceImpl(@Value("${app.security.jwt-key}") String secretKey,
                   @Value("${app.security.expiration-time-seconds}") long expirationSeconds) {
        this.secretKey = secretKey;
        this.expirationSeconds = expirationSeconds;
        this.jwtBuilder = Jwts.parserBuilder().setSigningKey(getKey()).build();
    }

    @Override
    public String createToken(String username) {
        Key key = getKey();
        return Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationSeconds)))
                .compact();
    }

    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secretKey),
                SignatureAlgorithm.HS256.getJcaName());
    }

    @Override
    public Jws<Claims> parseToken(String token) {
        return jwtBuilder.parseClaimsJws(token);
    }

    @Override
    public boolean validateToken(UserDetails user, String token) {
        Claims claims = parseToken(Objects.requireNonNull(token)).getBody();
        String username = claims.getSubject();

        return claims.getExpiration().before(Date.from(Instant.now()))
                && user.getUsername() == username;
    }

    @Override
    public String getUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }
}
