package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    final private InMemoryUserStorage userStorage;

    public void validateCreate(User newUser) {
        if (newUser == null) {
            throw new ValidationException("Пользователь не может быть null");
        }
        if (newUser.getEmail() == null || !newUser.getEmail().contains("@") || !isCharUniqueInString(newUser.getEmail(), "@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (newUser.getLogin() == null || newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (newUser.getBirthday() == null || newUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        log.debug("Валидация пользователя пройдена");
    }

    public void validateUpdate(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }
        validateCreate(newUser);
    }

    public Map<Long, User> getUsers() {
        return userStorage.getUsers();
    }

    public User getById(long id) {
        return userStorage.getById(id);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public void save(User newUser) {
        validateCreate(newUser);
        userStorage.save(newUser);
    }

    public void update(User newUser) {
        validateUpdate(newUser);
        userStorage.update(newUser);
    }

    public User addFriend(Long id, Long friendId) {
        if (id.equals(friendId)) throw new ValidationException("Пользователь не может добавить сам себя в друзья");
        User user1 = userStorage.getById(id);
        User user2 = userStorage.getById(friendId);
        if (user2 == null || user1 == null) throw new NotFoundException("Пользователь не найден");
        user1.getFriends().add(friendId);
        user2.getFriends().add(id);
        return user1;
    }

    public User deleteFriend(Long id, Long friendId) {
        User user1 = userStorage.getById(id);
        User user2 = userStorage.getById(friendId);
        if (user2 == null || user1 == null) throw new NotFoundException("Пользователь не найден");
        user1.getFriends().remove(friendId);
        user2.getFriends().remove(id);
        return user1;
    }

    public List<User> findMutualFriends(Long id, Long friendId) {
        if (userStorage.getById(id) == null || userStorage.getById(friendId) == null)
            throw new NotFoundException("Пользователь не найден");
        Set<Long> friendsUser1 = userStorage.getById(id).getFriends();
        Set<Long> friendsUser2 = userStorage.getById(friendId).getFriends();
        List<User> mutualFriends = new ArrayList<>();
        for (Long idFriend : friendsUser1) {
            if (friendsUser2.contains(idFriend)) mutualFriends.add(userStorage.getById(idFriend));
        }
        return mutualFriends;
    }

    private boolean isCharUniqueInString(String str, String ch) {
        return str.indexOf(ch) == str.lastIndexOf(ch);
    }
}
