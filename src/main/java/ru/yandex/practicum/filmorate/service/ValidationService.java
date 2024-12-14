package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface ValidationService {
    void validateCreate(User newUser);

    void validateUpdate(User newUser);

    void validateCreate(Film newFilm);

    void validateUpdate(Film newFilm);
}
