package com.defers.mypastebin.util;

public class MessagesUtils {
    private MessagesUtils(){}

    public static String getFormattedMessage(String message, Object... args) {
        String msg = String.format(message, args);
        return msg;
    }
}
