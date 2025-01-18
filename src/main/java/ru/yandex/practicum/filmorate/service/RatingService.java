package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.dal.RatingDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {
    final RatingDbStorage storage;

    public List<Rating> getAll(){
        return storage.findAll();
    }

    public Rating getById(Long id){
        return new Rating(id, storage.findById(id));
    }
}
