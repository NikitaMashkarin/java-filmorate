package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateCreate(Film newFilm) {
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (newFilm.getDescription() == null || newFilm.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }

        if (newFilm.getReleaseDate() == null || newFilm.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }

        if (newFilm.getDuration() == null || newFilm.getDuration().toMinutes() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        log.debug("Валидация фильма пройдена");
    }

    @Override
    public void validateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newFilm);
    }

    @Override
    public void validateCreate(User newUser) {
        if (newUser.getEmail() == null || !(newUser.getEmail().contains("@")) || !(isCharUniqueInString(newUser.getEmail(), "@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if (newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (newUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        log.debug("Валидация пользователя пройдена");
    }

    @Override
    public void validateUpdate(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newUser);
    }

    private boolean isCharUniqueInString(String str, String ch) {
        return str.indexOf(ch) == str.lastIndexOf(ch);
    }
}


