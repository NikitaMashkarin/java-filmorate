package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserStorage {

    User create(User user);

    User update(User user);

    Collection<User> getAll();

    Optional<User> findUserById(long id);
}
