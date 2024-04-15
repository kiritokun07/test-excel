package com.kirito.excel.utils;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注解校验
 */
public class ValidationUtil {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    public static <T> void validateEntity(T entity) {
        Set<ConstraintViolation<T>> violations = VALIDATOR.validate(entity);
        if (!violations.isEmpty()) {
            String errMsg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(","));
            throw new IllegalArgumentException(errMsg);
        }
    }

    public static <T> void validateEntities(List<T> entities) {
        for (T entity : entities) {
            validateEntity(entity);
        }
    }

}