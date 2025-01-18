package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Qualifier
@Repository
public class FilmDbStorage extends BaseRepository {

    public FilmDbStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Long add(Film film){
        long id = insert("INSERT INTO films(name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?) returning id",
                film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()), film.getDuration(), film.getRating().getId());
        film.setId(id);
        return id;
    }

    public Film update(Film film) {
        update("UPDATE users SET name = ?, description = ?, releaseDate = ? , duration = ? , rating = ? WHERE id = ?",
                film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()), film.getDuration(),
                film.getRating().getId(), film.getId());
        return film;
    }

    public Optional<Film> findById(Long id) {
        return findOne("SELECT * FROM films WHERE film_id = ?", id);
    }

    public List<Film> findAll() {
        return findMany("SELECT * FROM friends");
    }

    public boolean setGenre(Long idFilm, Long idGenre) {
        if(findGenre(idFilm, idGenre)) {
            insert("INSERT INTO Genre_film(film_id, genre_id) VALUES (?, ?)", idFilm, idGenre);
            return true;
        }
        return false;
    }

    public boolean deleteGenre(Long idFilm, Long idGenre) {
        return delete("DELETE FROM Genre_film WHERE film_id = ? AND genre_id = ?", idFilm, idGenre);
    }

    public boolean addLike(Long idFilm, Long idUser) {
        if(findLike(idFilm, idUser)) {
            insert("INSERT INTO Likes(film_id, user_id) VALUES (?, ?)", idFilm, idUser);
            return true;
        }
        return false;
    }

    public List<Film> mostPopulars(Integer limit) {
        return findMany("SELECT f.* FROM FILMS f JOIN LIKES l ON f.FILM_ID = l.FILM_ID GROUP BY f.FILM_ID ORDER BY COUNT(l.FILM_ID) DESC LIMIT + ,",
                limit);
    }

    public boolean deleteLike(Long idFilm, Long idUser) {
        return delete("DELETE FROM Likes WHERE film_id = ? AND user_id = ?", idFilm, idUser);
    }

    private boolean findGenre(Long idFilm, Long idGenre) {
        return findOne("SELECT * FROM Genre_film WHERE film_id = ? AND genre_id = ?", idFilm, idGenre).isEmpty();
    }

    private boolean findLike(Long idFilm, Long idUser) {
        return findOne("SELECT * FROM Likes WHERE film_id = ? AND user_id = ?", idFilm, idUser).isEmpty();
    }
}
