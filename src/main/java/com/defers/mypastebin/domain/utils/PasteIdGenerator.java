package com.defers.mypastebin.domain.utils;

import java.util.UUID;

public class PasteIdGenerator{
    public static String generate() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }
}
