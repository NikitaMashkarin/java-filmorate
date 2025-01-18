package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmDbStorage;
import ru.yandex.practicum.filmorate.dal.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    final FilmDbStorage filmStorage;
    final UserDbStorage userStorage;

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

        if (newFilm.getDuration() == null || newFilm.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        log.debug("Валидация фильма пройдена");
    }

    public Collection<Film> getAll() {
        return filmStorage.findAll();
    }

    public void validateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newFilm);
    }

    public void save(Film newFilm) {
        validateCreate(newFilm);
        filmStorage.add(newFilm);
    }

    public void update(Film newFilm) {
        validateUpdate(newFilm);
        filmStorage.update(newFilm);
    }

    public Film getById(Long id) {
        Optional<Film> newFilm =  filmStorage.findById(id);
        if(newFilm.isPresent()) return newFilm.get();
        throw new NotFoundException("Фильм с id = " + id + " не найден");
    }

    public Film addLike(Long idUser, Long idFilm) {
        Optional<Film> film =  filmStorage.findById(idFilm);
        if(film.isEmpty()) throw new NotFoundException("Фильм с ID " + idFilm + " не найден");
        if(userStorage.findById(idUser).isEmpty()) throw new NotFoundException("Пользователь с ID " + idUser + " не найден");
        filmStorage.addLike(idFilm, idUser);
        return film.get();
    }

    public Film deleteLike(Long idUser, Long idFilm) {
        Optional<Film> film =  filmStorage.findById(idFilm);
        if(film.isEmpty()) throw new NotFoundException("Фильм с ID " + idFilm + " не найден");
        if(userStorage.findById(idUser).isEmpty()) throw new NotFoundException("Пользователь с ID " + idUser + " не найден");
        filmStorage.deleteLike(idFilm, idUser);
        return film.get();
    }

    public Collection<Film> findPopular(int count) {
        return filmStorage.mostPopulars(count);
    }
}
