package ru.yandex.practicum.filmorate.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final Map<Long, User> users = new HashMap<>();

    public void update(User user) {
        if (users.containsKey(user.getId())) {
            User oldUser = users.get(user.getId());
            oldUser.setLogin(user.getLogin());
            oldUser.setBirthday(user.getBirthday());
            oldUser.setEmail(user.getEmail());
            if (oldUser.getName().isEmpty()) {
                oldUser.setName(user.getName());
            }
        } else {
            log.warn("Фильм с id: {} - не найден", user.getId());
            throw new ValidationException("Фильм с указанным id не найден");
        }
    }

    public void save(User user) {
        user.setId(getNextId());
        users.put(user.getId(), user);
    }

    public Collection<User> getAll() {
        return users.values();
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
