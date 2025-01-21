package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {

    private static final Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateCorrectAllData() {
        Film film = Film.builder()
                .id(1L)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.parse("1967-04-23"))
                .duration(112)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);
        assertEquals(0, violations.size(), "Violations found");
    }

    @Test
    void validateNotCorrectName() {
        Film film = Film.builder()
                .id(1L)
                .name(" ")
                .description("description")
                .releaseDate(LocalDate.parse("1967-04-23"))
                .duration(112)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }

    @Test
    void validateLongDescription() {
        Film film = Film.builder()
                .id(1L)
                .name("name")
                .description("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription")
                .releaseDate(LocalDate.parse("1967-04-23"))
                .duration(112)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }

    @Test
    void validateNotNullReleaseDate() {
        Film film = Film.builder()
                .id(1L)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.parse("1867-04-23"))
                .duration(112)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }

    @Test
    void validatePositiveDuration() {
        Film film = Film.builder()
                .id(1L)
                .name("name")
                .description("description")
                .releaseDate(LocalDate.parse("1967-04-23"))
                .duration(0)
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(film, Create.class);
        assertEquals(1, violations.size(), "Violations not found");
    }

}