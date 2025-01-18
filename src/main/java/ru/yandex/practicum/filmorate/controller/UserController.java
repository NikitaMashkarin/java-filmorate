package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/{id}/friends")
    public Collection<Long> findFriendsById(@PathVariable Long id) {
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findMutualFriends(@PathVariable Long id,
                                        @PathVariable Long otherId) {
        log.info("Общие друзья пользователя " + id + " и " + otherId);
        return service.findMutualFriends(id, otherId);
    }


    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Collection<User> findAll() {
        return service.getAll();
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("Создание пользователя: {} - началось", newUser);
        service.save(newUser);
        log.info("Создание пользователя закончилось");
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User newUser) {
        log.info("Изменение пользователя: {} - началось", newUser);
        service.update(newUser);
        log.info("Изменение пользователя закончилось");
        return service.getById(newUser.getId());
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) {
        log.info("Пользователь " + id + " добавляет пользователя " + friendId + " в друзья");
        User user = service.addFriend(id, friendId);
        log.info("Пользователи теперь друзья");
        return user;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id,
                             @PathVariable Long friendId) {
        log.info("Пользователь " + id + " удаляет пользователя " + friendId + " из друзей");
        User user = service.deleteFriend(id, friendId);
        log.info("Пользователи теперь не друзья");
        return user;
    }


}