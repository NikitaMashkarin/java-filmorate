package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class ValidateService implements ValidationService {
    @Override
    public void validateCreate(Film newFilm) {
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (newFilm.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }

        if (newFilm.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }

        if (newFilm.getDuration().toMinutes() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    @Override
    public void validateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (newFilm.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }

        if (newFilm.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }

        if (newFilm.getDuration().toMinutes() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    @Override
    public void validateCreate(User newUser) {
        if (newUser.getEmail().isBlank() || !(newUser.getEmail().contains("@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if (newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (newUser.getBirthday().isBefore(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

        if (newUser.getName().isBlank()) {
            newUser.setName(newUser.getLogin());
        }
    }

    @Override
    public void validateUpdate(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        if (newUser.getEmail().isBlank() || !(newUser.getEmail().contains("@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if (newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (newUser.getBirthday().isBefore(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
