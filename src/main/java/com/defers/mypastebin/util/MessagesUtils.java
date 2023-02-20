package com.defers.mypastebin.util;

// TEST Branch master
public class MessagesUtils {
    public static String getFormattedMessage(String message, Object... args) {
        String msg = String.format(message, args);
        return msg;
    }
}
