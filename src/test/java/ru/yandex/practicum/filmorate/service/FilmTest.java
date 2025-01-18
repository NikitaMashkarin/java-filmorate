package ru.yandex.practicum.filmorate.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@AutoConfigureTestDatabase
public class FilmTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void correctlyFieldFilmTest() {
        final Film film = new Film("Титаник", "Корабль", LocalDate.of(2000, 11, 14), 160L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertTrue(violations.isEmpty(), "Заполнено все верно");
    }

    @Test
    void correctlyNameFilmTest() {
        final Film film = new Film("", "Корабль", LocalDate.of(2000, 11, 14), 160L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Пустое название фильма");
    }

    @Test
    void correctlyDescriptionFilmTest() {
        final Film film = new Film( "Титаник", "_________________________________________" +
                "________________________________________________________________________________________" +
                "________________________________________________________________________", LocalDate.of(2000, 11, 14), 160L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Описание более 200 символов");
    }

    @Test
    void correctlyDurationFilmTest() {
        final Film film = new Film("Титаник", "Корабль", LocalDate.of(2000, 11, 14), -3L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Продолжительность фильмов должна быть больше 0");
    }

    @Test
    void correctlyDateFilmTestFirst() {
        final Film film = new Film( "Титаник", "Корабль", LocalDate.of(1985, 12, 27),145L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        System.out.println(violations);
        Assertions.assertTrue(violations.isEmpty(), "Фильм не раньше 1895-12-28");
    }

    @Test
    void correctlyDateFilmTestSecond() {
        final Film film = new Film( "Титаник", "Корабль", LocalDate.of(1785, 12, 27),145L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        Assertions.assertFalse(violations.isEmpty(), "Фильм раньше 1895-12-28");
    }
}