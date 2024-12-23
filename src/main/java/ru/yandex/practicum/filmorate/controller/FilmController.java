package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService validate = new FilmService();
    private final InMemoryFilmStorage repository = new InMemoryFilmStorage();

    @GetMapping
    public Collection<Film> findAll() {
        return repository.getAll();
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("Создание фильма: {} - началось", newFilm);
        validate.validateCreate(newFilm);
        repository.save(newFilm);
        log.info("Создание фильма закончилось");
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Изменение фильма с ID: {} - началось", newFilm.getId());
        validate.validateUpdate(newFilm);
        repository.update(newFilm);
        log.info("Изменение фильма с ID: {} - закончилось", repository.getById(newFilm.getId()));
        return repository.getById(newFilm.getId());
    }
}