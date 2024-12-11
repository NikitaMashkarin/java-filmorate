package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidateService;
import ru.yandex.practicum.filmorate.service.ValidationService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    ValidationService validate = new ValidateService();
    UserRepository repository = new UserRepository();

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