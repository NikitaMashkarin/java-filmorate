package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    void update(Film newFilm);

    void save(Film film);

    Collection<Film> getAll();

    Film getById(long id);
}
