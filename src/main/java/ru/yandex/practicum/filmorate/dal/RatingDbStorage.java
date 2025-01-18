package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public class RatingDbStorage extends BaseRepository {
    public RatingDbStorage(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
        super(jdbc, mapper);
    }

    public String findById(Long id) {
        Optional<String> name = findOne("SELECT rating FROM Rating WHERE rating_id = ?", id);
        if(name.isPresent()) return name.get();
        throw new NotFoundException("Рейтинга с id = " + id + " не существует");
    }

    public List<Rating> findAll(){
        return findMany("SELECT rating FROM Rating");
    }
}
