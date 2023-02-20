package com.defers.mypastebin.repository.converter;

import com.defers.mypastebin.domain.Paste;
import com.defers.mypastebin.domain.User;
import com.defers.mypastebin.repository.UserRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PasteConverter {
    private static UserRepository userRepository;

    public static Mono<Paste> fromRows(List<Map<String, Object>> rows) {
        Map<String, Object> row = rows.get(0);
        User user = null;
        if (Objects.nonNull(row.get("u_username"))) {
            user = User.builder()
                    .username((String) row.get("u_username"))
                    .password((String) row.get("u_password"))
                    .email((String) row.get("u_email"))
                    .build();
        }
        Mono<Paste> paste = Mono.just(
                Paste.builder()
                        .id((String) row.get("p_id"))
                        .textDescription((String) row.get("p_text_description"))
                        .createdDate((LocalDateTime) row.get("p_created_date"))
                        .updatedDate((LocalDateTime) row.get("p_updated_date"))
                        .user(user)
                        .build()
        );
        return paste;
    }

}
