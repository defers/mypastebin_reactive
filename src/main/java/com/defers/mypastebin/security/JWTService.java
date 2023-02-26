package com.defers.mypastebin.security;

public interface JWTService {
    String createToken(String username);
    String parseToken();
    boolean validateToken();
}
