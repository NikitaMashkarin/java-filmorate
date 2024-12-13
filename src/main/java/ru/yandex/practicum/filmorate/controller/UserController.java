package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidationService;
import ru.yandex.practicum.filmorate.service.ValidationServiceImpl;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final ValidationService validate = new ValidationServiceImpl();
    private final UserRepository repository = new UserRepository();

    @GetMapping
    public Collection<User> findAll() {
        return repository.getAll();
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("Создание пользователя: {} - началось", newUser);
        validate.validateCreate(newUser);
        repository.save(newUser);
        log.info("Создание пользователя закончилось");
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("Изменение пользователя: {} - началось", newUser);
        validate.validateUpdate(newUser);
        repository.update(newUser);
        log.info("Изменение пользователя закончилось");
        return repository.getById(newUser.getId());
    }
}