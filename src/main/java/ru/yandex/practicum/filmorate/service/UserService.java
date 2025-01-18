package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    final UserDbStorage userStorage;

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

    public List<Long> getFriends(Long id){
        if(userStorage.findById(id).isEmpty()) throw new NotFoundException("Пользователя с id " + id + " не существует");
        return userStorage.findAllFriends(id);
    }
    public List<User> getUsers() {
        return userStorage.findAll();
    }

    public User getById(long id) {
        Optional<User> user = userStorage.findById(id);
        if(user.isEmpty()) throw new NotFoundException("Пользователь с id = " + id + " не найден");
        return  user.get();
    }

    public Collection<User> getAll() {
        return userStorage.findAll();
    }

    public void save(User newUser) {
        validateCreate(newUser);
        userStorage.add(newUser);
    }

    public void update(User newUser) {
        validateUpdate(newUser);
        userStorage.update(newUser);
    }

    public User addFriend(Long id, Long friendId) {
        if (id.equals(friendId)) throw new ValidationException("Пользователь не может добавить сам себя в друзья");
        Optional<User> user1 = userStorage.findById(id);
        Optional<User> user2 = userStorage.findById(friendId);
        if (user1.isEmpty() || user2.isEmpty()) throw new NotFoundException("Пользователь не найден");
        userStorage.addRequestsFriendship(id, friendId);
        return user1.get();
    }

    public User deleteFriend(Long id, Long friendId) {
        if (id.equals(friendId)) throw new ValidationException("Пользователь не может удалить сам себя из друзья");
        Optional<User> user1 = userStorage.findById(id);
        Optional<User> user2 = userStorage.findById(friendId);
        if (user1.isEmpty() || user2.isEmpty()) throw new NotFoundException("Пользователь не найден");
        userStorage.deleteFriends(id, friendId);
        return user1.get();
    }

    public List<User> findMutualFriends(Long id, Long friendId) {
        getById(id);
        getById(friendId);
        List<User> commonFriend = new ArrayList<>();
        Set<Long> common = new HashSet<>(userStorage.findAllFriends(id));
        common.retainAll(userStorage.findAllFriends(friendId));
        for (Long idFriendUser : common) {
            commonFriend.add(getById(idFriendUser));
        }
        return commonFriend;
    }

    private boolean isCharUniqueInString(String str, String ch) {
        return str.indexOf(ch) == str.lastIndexOf(ch);
    }
}
