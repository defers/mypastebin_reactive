package com.defers.mypastebin.exception;

import lombok.Data;

import java.util.Set;

@Data
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(Set<String> validations) {
        super(String.join("->\n", validations));
    }
}
