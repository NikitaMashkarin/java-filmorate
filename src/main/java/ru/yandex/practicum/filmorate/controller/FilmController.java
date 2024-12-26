package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;
    private final InMemoryFilmStorage repository;

    @Autowired
    public FilmController(FilmService service, InMemoryFilmStorage repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/popular")
    public Collection<Film> findPopularFilm(@RequestParam final int count) {
        return service.findPopular(count);
    }

    @GetMapping
    public Collection<Film> findAll() {
        return repository.getAll();
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("Создание фильма: {} - началось", newFilm);
        service.validateCreate(newFilm);
        repository.save(newFilm);
        log.info("Создание фильма закончилось");
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Изменение фильма с ID: {} - началось", newFilm.getId());
        service.validateUpdate(newFilm);
        repository.update(newFilm);
        log.info("Изменение фильма с ID: {} - закончилось", repository.getById(newFilm.getId()));
        return repository.getById(newFilm.getId());
    }

    @PutMapping("{id}/like/{userId}")
    public Film addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        Film film = service.addLike(id, userId);
        log.info("Пользователь " + userId + " поставил лайк фильму " + id);
        return film;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        Film film = service.deleteLike(id, userId);
        log.info("Пользователь " + userId + " удалил лайк фильму " + id);
        return film;
    }
}