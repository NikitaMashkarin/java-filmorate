package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final InMemoryUserStorage repository;

    @Autowired
    public UserController(UserService service, InMemoryUserStorage repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/{id}/friends")
    public Set<Long> findFriendsById(@PathVariable Long id) {
        if (repository.getUsers().containsKey(id)) return repository.getById(id).getFriends();
        throw new NotFoundException("Пользователя с id " + id + " не существует");
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<Long> findMutualFriends(@PathVariable Long id,
                                        @PathVariable Long otherId) {
        log.info("Общие друзья пользователя " + id + " и " + otherId);
        return service.findMutualFriends(id, otherId, repository);
    }


    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        if (repository.getUsers().containsKey(id)) return repository.getById(id);
        throw new NotFoundException("Пользователя с id " + id + " не существует");
    }

    @GetMapping
    public Collection<User> findAll() {
        return repository.getAll();
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("Создание пользователя: {} - началось", newUser);
        service.validateCreate(newUser);
        repository.save(newUser);
        log.info("Создание пользователя закончилось");
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("Изменение пользователя: {} - началось", newUser);
        service.validateUpdate(newUser);
        repository.update(newUser);
        log.info("Изменение пользователя закончилось");
        return repository.getById(newUser.getId());
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) {
        log.info("Пользователь " + id + " добавляет пользователя " + friendId + " в друзья");
        User user = service.addFriend(id, friendId, repository);
        log.info("Пользователи теперь друзья");
        return user;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id,
                             @PathVariable Long friendId) {
        log.info("Пользователь " + id + " удаляет пользователя " + friendId + " из друзей");
        User user = service.deleteFriend(id, friendId, repository);
        log.info("Пользователи теперь не друзья");
        return user;
    }


}