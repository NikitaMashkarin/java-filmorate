package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
@Getter
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();

    public void update(User user) {
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            oldUser.setLogin(user.getLogin());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            if (user.getName() == null) {
                oldUser.setName(user.getLogin());
            } else {
                oldUser.setName(user.getName());
            }
        } else {
            log.warn("Фильм с id: {} - не найден", user.getId());
            throw new NotFoundException("Фильм с указанным id не найден");
        }
    }

    public User save(User user) {
        user.setId(getNextId());
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User getById(long id) {
        return users.get(id);
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
