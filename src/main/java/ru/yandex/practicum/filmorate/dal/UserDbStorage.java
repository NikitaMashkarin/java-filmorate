package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Qualifier
@Repository
public class UserDbStorage extends BaseRepository {

    public UserDbStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Long add(User user) {
        long id = insert("INSERT INTO users(email, login, name, birthday) VALUES (?, ?, ?, ?) returning id",
                user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()));
        user.setId(id);
        return id;
    }

    public User update(User user) {
        update("UPDATE users SET email = ?, login = ?, name = ? , birthday = ? WHERE id = ?",
                user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()), user.getId());
        return user;
    }


    public Optional<User> findById(long userId) {
        return findOne("SELECT * FROM users WHERE users_id = ?", userId);
    }

    public List<User> findAll() {
        return findMany("SELECT * FROM users");
    }

    public boolean addRequestsFriendship(Long idUser, Long idFriend){
        if(!findRequestsFriendship(idUser, idFriend)){
            Map<String, Long> map = new HashMap<>();
            map.put("user_id", idUser);
            map.put("friend_id", idFriend);
            insert("INSERT INTO Friends(user_id, friend_id) VALUES (?, ?)", idUser, idFriend);
            return true;
        }
        return false;
    }

    public boolean deleteFriends(Long idUser, Long idFriend) {
        delete("DELETE FROM Friends WHERE user_id = ? AND friend_id = ?", idUser, idFriend);
        if(!findRequestsFriendship(idUser, idFriend)) return true;
        return false;
    }

    public List<Long> findAllFriends(Long idUser) {
        return findMany("SELECT friend_id FROM Friends WHERE user_id = ?", idUser);
    }

    private boolean findRequestsFriendship(Long firstId, Long secondId) {
        String sqlQuery = String.format("SELECT COUNT(*)\n" +
                "FROM FRIENDSHIP_REQUESTS\n" +
                "WHERE (SENDER_ID = %d OR RECIPIENT_ID = %d)" +
                " AND (SENDER_ID = %d OR RECIPIENT_ID = %d)", firstId, firstId, secondId, secondId);

        return jdbc.queryForObject(sqlQuery, Integer.class) == 1;
    }
}
