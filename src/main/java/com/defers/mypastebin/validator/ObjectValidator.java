package com.defers.mypastebin.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> implements com.defers.mypastebin.validator.Validator<T> {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Override
    public Set<String> validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        if (violations.size() != 0) {
            return violations.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
