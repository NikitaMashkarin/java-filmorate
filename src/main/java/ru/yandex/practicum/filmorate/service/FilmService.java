package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

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

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public void validateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newFilm);
    }

    public Film addLike(Long idUser, Long idFilm) {
        if (filmStorage.getById(idFilm) == null) throw new NotFoundException("Фильм с ID " + idFilm + " не найден");
        if (userStorage.getById(idUser) == null)
            throw new NotFoundException("Пользователь с ID " + idUser + " не найден");
        Film film = filmStorage.getById(idFilm);
        film.getLikes().add(idUser);
        return film;
    }

    public Film deleteLike(Long idUser, Long idFilm) {
        if (filmStorage.getById(idFilm) == null) throw new NotFoundException("Фильм с ID " + idFilm + " не найден");
        if (userStorage.getById(idUser) == null)
            throw new NotFoundException("Пользователь с ID " + idUser + " не найден");
        Film film = filmStorage.getById(idFilm);
        film.getLikes().remove(idUser);
        return film;
    }

    public Collection<Film> findPopular(int count) {
        return filmStorage.getAll().stream()
                .sorted((film1, film2) -> {
                    if (film2.getQuantityLikes().compareTo(film1.getQuantityLikes()) == 0) {
                        return film1.getName().compareTo(film2.getName());
                    }
                    return film2.getQuantityLikes().compareTo(film1.getQuantityLikes());
                })
                .limit(count)
                .collect(Collectors.toList());
    }
}
