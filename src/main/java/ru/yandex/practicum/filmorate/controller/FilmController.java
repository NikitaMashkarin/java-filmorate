package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final ValidationService validate = new ValidateService();
    private final FilmRepository repository = new FilmRepository();
    private final Logger log = LoggerFactory.getLogger(FilmController.class);

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