package com.defers.mypastebin.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {
    public static String encodePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(rawPassword);
    }
}
