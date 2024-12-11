package ru.yandex.practicum.filmorate.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FilmRepository {
    private final static Logger log = LoggerFactory.getLogger(FilmRepository.class);
    private final Map<Long, Film> films = new HashMap<>();

    public void update(Film newFilm) {
        if (films.containsKey(newFilm.getId())) {
            Film oldFilm = films.get(newFilm.getId());
            oldFilm.setDescription(newFilm.getDescription());
            oldFilm.setName(newFilm.getName());
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
            oldFilm.setDuration(newFilm.getDuration());
        } else {
            log.warn("Фильм с id: {} - не найден", newFilm.getId());
            throw new ValidationException("Фильм с указанным id не найден");
        }
    }

    public void save(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
    }

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film getById(long id) {
        return films.get(id);
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
