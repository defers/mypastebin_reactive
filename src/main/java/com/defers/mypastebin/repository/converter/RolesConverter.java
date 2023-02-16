package com.defers.mypastebin.repository.converter;

import com.defers.mypastebin.domain.Role;

import java.util.Map;

public class RolesConverter {
    public static Role fromRow(Map<String, Object> row) {

        Role role = Role.builder()
                .id((long) row.get("r_id"))
                .name((String) row.get("r_name"))
                .build();
        return role;
    }
}
