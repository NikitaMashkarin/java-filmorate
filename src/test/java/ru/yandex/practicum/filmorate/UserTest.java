package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateName() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .login("login")
                .email("name1@ya.ru")
                .birthday(LocalDate.parse("1999-09-12"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertTrue(violations.isEmpty(), "Violation found");
    }

    @Test
    void validateCorrectAllData() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .login("login")
                .email("name1@ya.ru")
                .birthday(LocalDate.parse("1999-09-12"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(0, violations.size(), "Violations found");
    }

    @Test
    void validateEmptyEmail() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .login("login")
                .email(" ")
                .birthday(LocalDate.parse("1999-09-12"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(2, violations.size(), "Violations not found");
    }

    @Test
    void validateNotCorrectEmail() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .login("login")
                .email("name.ya.ru")
                .birthday(LocalDate.parse("1999-09-12"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }

    @Test
    void validateNotCorrectBirthDate() {
        User user = User.builder()
                .id(1L)
                .name("name")
                .login("login")
                .email("name1@ya.ru")
                .birthday(LocalDate.parse("2025-11-22"))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }
}