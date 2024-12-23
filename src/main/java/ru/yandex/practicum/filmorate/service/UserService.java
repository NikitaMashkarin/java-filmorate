package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserService {
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

    public void addFriend(User user1, User user2){
        user1.getFriends().add(user2.getId());
        user2.getFriends().add(user1.getId());
    }

    public void deleteFriend(User user1, User user2){
        user1.getFriends().remove(user2.getId());
        user2.getFriends().remove(user1.getId());
    }

    public List<Long> findMutualFriends(User user1, User user2){
        Set<Long> friendsUser1 = user1.getFriends();
        Set<Long> friendsUser2 = user2.getFriends();
        List<Long> mutualFriends = new ArrayList<>();
        for(Long idFriend : friendsUser1){
            if(friendsUser2.contains(idFriend)) mutualFriends.add(idFriend);
        }
        return mutualFriends;
    }

    private boolean isCharUniqueInString(String str, String ch) {
        return str.indexOf(ch) == str.lastIndexOf(ch);
    }
}
