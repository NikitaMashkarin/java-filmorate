package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDbStorage extends BaseRepository {

    public GenreDbStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public String findById(Long id){
        Optional<String> name = findOne("SELECT name FROM genre WHERE genre_id = ?", id);
        if(name.isPresent()) return name.get();
        throw new NotFoundException("Рейтинга с id = " + id + " не существует");
    }

    public List<Genre> findAll(){
        return findMany("SELECT name FROM genre");
    }

    public List<Genre> getGenres(Integer filmId){
        return findMany("SELECT name FROM Genre_film WHERE film_id = ?", filmId);
    }
}
