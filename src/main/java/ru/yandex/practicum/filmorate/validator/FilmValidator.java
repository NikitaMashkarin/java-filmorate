package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator implements ConstraintValidator<FilmValid, Film> {

    @Override
    public boolean isValid(Film film, ConstraintValidatorContext constraintValidatorContext) {
        return !film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28));
    }
}
