package com.defers.mypastebin.repository.converter;

import com.defers.mypastebin.domain.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public static Mono<User> fromRows(List<Map<String, Object>> rows) {
        Mono<User> user = Mono.just(
                User.builder()
                        .username((String) rows.get(0).get("u_username"))
                        .password((String) rows.get(0).get("u_password"))
                        .email((String) rows.get(0).get("u_email"))
                        .roles(rows.stream()
                                .filter(e -> e.get("r_id") != null)
                                .map(e -> RolesConverter.fromRow(e))
                                .collect(Collectors.toSet()))
                        .build()
                );
        return user;
    }

    public static Mono<User> fromRow(Map<String, Object> row) {
        return Mono.just(
                User.builder()
                        .username((String) row.get("username"))
                        .password((String) row.get("password"))
                        .email((String) row.get("email"))
                        .build()
        );
    }
}
