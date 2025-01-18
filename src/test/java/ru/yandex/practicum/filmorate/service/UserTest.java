package ru.yandex.practicum.filmorate.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

@AutoConfigureTestDatabase
public class UserTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    @Test
    void correctlyFilledUserTest() {
        final User user = new User( "qw@mail.ru", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty(),"Заполнено все верно");
    }
    @Test
    void correctlyEmailUserTestFirst() {
        final User user = new User( "badEmail", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Неверно заполнен email");
    }
    @Test
    void correctlyEmailUserTestSecond() {
        final User user = new User( "", "test", "name", LocalDate.of(2000, 12, 12));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Пустой email");
    }

    @Test
    void correctlyBirthdayUserTest() {
        final User user = new User( "qw@mail.ru", "test", "name", LocalDate.of(2025, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Будущая дата");
    }
    @Test
    void correctlyLoginUserTest() {
        final User user = new User( "qw@mail.ru", "", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Пустой логин");
    }

    @Test
    void correctlyLoginUserTestSecond() {
        final User user = new User( "qw@mail.ru", "da da", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertFalse(violations.isEmpty(),"Пустой логин");
    }

    @Test
    void correctlyLoginUserTestThird() {
        final User user = new User( "qw@mail.ru", "dada", "name", LocalDate.of(2000, 11, 14));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertTrue(violations.isEmpty(),"Пустой логин");
    }
}
