package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService service;

    @GetMapping("/popular")
    public Collection<Film> findPopularFilm(@RequestParam(defaultValue = "10") int count) {
        return service.findPopular(count);
    }

    @GetMapping
    public Collection<Film> findAll() {
        return service.getAll();
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        log.info("Создание фильма: {} - началось", newFilm);
        service.save(newFilm);
        log.info("Создание фильма закончилось");
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        log.info("Изменение фильма с ID: {} - началось", newFilm.getId());
        service.update(newFilm);
        Film film = service.getById(newFilm.getId());
        log.info("Изменение фильма с ID: {} - закончилось", film);
        return film;
    }

    @PutMapping("{id}/like/{userId}")
    public Film addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        Film film = service.addLike(userId, id);
        log.info("Пользователь " + userId + " поставил лайк фильму " + id);
        return film;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        Film film = service.deleteLike(userId, id);
        log.info("Пользователь " + userId + " удалил лайк фильму " + id);
        return film;
    }
}