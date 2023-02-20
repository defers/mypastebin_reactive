package com.defers.mypastebin.validator;

import java.util.Set;

public interface Validator<T> {
    Set<String> validate(T objectToValidate);
}
