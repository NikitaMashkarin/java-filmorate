package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.GenreDbStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    final GenreDbStorage storage;

    public List<Genre> getAll(){
        return storage.findAll();
    }

    public List<Genre> getGenresId(Integer id){
        return storage.getGenres(id);
    }

    public Genre getById(Long id){
        return new Genre(id, storage.findById(id));
    }
}
