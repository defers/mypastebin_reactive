package com.defers.mypastebin.util;

import com.defers.mypastebin.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestRepoImpl implements TestRepo {
    @Override
    public User findByUsername(String username) {
        return User.builder()
                .username("test1")
                .password("test1")
                .email("email1555@mail.com")
                .createdDate(LocalDateTime.of(2023, 1, 10, 15, 10, 0))
                .updatedDate(LocalDateTime.of(2023, 1, 12, 14, 12, 0))
                .build();
    }
}
