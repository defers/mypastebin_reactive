package com.defers.mypastebin.exception;

public class PasteNotFoundException extends RuntimeException {
    public PasteNotFoundException(String msg) {
        super(msg);
    }
}
