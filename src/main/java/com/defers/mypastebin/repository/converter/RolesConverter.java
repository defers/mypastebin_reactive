package com.defers.mypastebin.repository.converter;

import com.defers.mypastebin.domain.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RolesConverter {
    public static Role fromRow(Map<String, Object> row) {
        return Role.builder()
                .id((long) row.get("r_id"))
                .name((String) row.get("r_name"))
                .build();
    }
}
