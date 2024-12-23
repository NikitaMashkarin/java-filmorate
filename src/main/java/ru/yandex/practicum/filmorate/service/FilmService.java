package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class FilmService {
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

    public void validateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newFilm);
    }

    public void addLike(Film film, User user){
        film.getLikes().add(user.getId());
        film.setQuantityLikes(film.getQuantityLikes() + 1);
    }

    public void deleteLike(Film film, User user){
        film.getLikes().remove(user.getId());
        film.setQuantityLikes(film.getQuantityLikes() - 1);
    }

    public List<Film> find10Popular(FilmStorage filmStorage){
        Set<Film> films = new TreeSet<>(filmStorage.getAll());
        List<Film> filmList = new LinkedList<>();
        int count = 0;
        for (Film film : films) {
            filmList.add(film);
            count++;
            if (count == 10) {
                break;
            }
        }
        return filmList;
    }
}
