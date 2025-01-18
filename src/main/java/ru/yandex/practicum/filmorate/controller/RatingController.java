package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {
    final RatingService service;

    @GetMapping()
    public List<Rating> getAll() {
        log.info("MpaController. getMpa. Получение списка категорий");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getById(@PathVariable Long id) {
        log.info("MpaController. getMpaById. Получение категории по id");
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }
}
