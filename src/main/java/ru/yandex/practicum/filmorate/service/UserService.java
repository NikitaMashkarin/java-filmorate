package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    public UserService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void validateCreate(User newUser) {
        if (newUser.getEmail() == null || !(newUser.getEmail().contains("@")) || !(isCharUniqueInString(newUser.getEmail(), "@"))) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }

        if (newUser.getLogin().isBlank() || newUser.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (newUser.getBirthday().isAfter(LocalDate.now())) {
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

    public User addFriend(Long id, Long friendId, InMemoryUserStorage userStorage) {
        User user1 = userStorage.getById(id);
        User user2 = userStorage.getById(friendId);
        if (user2 == null || user1 == null) throw new NotFoundException("Пользователь не найден");
        user1.getFriends().add(friendId);
        user2.getFriends().add(id);
        return user1;
    }

    public User deleteFriend(Long id, Long friendId, InMemoryUserStorage userStorage) {
        User user1 = userStorage.getById(id);
        User user2 = userStorage.getById(friendId);
        if (user2 == null || user1 == null) throw new NotFoundException("Пользователь не найден");
        user1.getFriends().remove(friendId);
        user2.getFriends().remove(id);
        return user1;
    }

    public List<Long> findMutualFriends(Long id, Long friendId, InMemoryUserStorage userStorage) {
        if (userStorage.getById(id) == null || userStorage.getById(friendId) == null)
            throw new NotFoundException("Пользователь не найден");
        Set<Long> friendsUser1 = userStorage.getById(id).getFriends();
        Set<Long> friendsUser2 = userStorage.getById(friendId).getFriends();
        List<Long> mutualFriends = new ArrayList<>();
        for (Long idFriend : friendsUser1) {
            if (friendsUser2.contains(idFriend)) mutualFriends.add(idFriend);
        }
        return mutualFriends;
    }

    private boolean isCharUniqueInString(String str, String ch) {
        return str.indexOf(ch) == str.lastIndexOf(ch);
    }
}
