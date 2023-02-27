package com.defers.mypastebin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String createToken(String username);
    String getUsername(String token);
    Jws<Claims> parseToken(String token);
    boolean validateToken(UserDetails user, String token);
}
