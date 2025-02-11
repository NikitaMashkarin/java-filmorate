package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    Collection<Film> getAll();

    Optional<Film> findFilmById(long id);

    Optional<Film> findFilmByName(String name);

    void addLike(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);

    Integer getLikesCount(Long filmId);
}