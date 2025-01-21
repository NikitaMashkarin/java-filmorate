package ru.yandex.practicum.filmorate.dao;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GenreStorage {
    List<Genre> findGenresByFilmId(Long id);

    List<Genre> getAll();

    Optional<Genre> getGenre(Long genreId);

    void setFilmGenres(Film film, List<Long> genreIds);

    void deleteFilmGenres(Film film, List<Long> genreIds);

    LinkedHashSet<Genre> findByIds(List<Long> ids);
}
