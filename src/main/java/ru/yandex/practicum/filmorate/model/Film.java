package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import ru.yandex.practicum.filmorate.data_conversion.*;
import ru.yandex.practicum.filmorate.validator.FilmValid;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FilmValid
public class Film {
//    private Long id;
//    private String name;
//    private String description;
//    private LocalDate releaseDate;
//    @JsonSerialize(using = CustomDurationSerializer.class)
//    @JsonDeserialize(using = CustomDurationDeserializer.class)
//    private Duration duration;
//    private final Set<Long> likes = new HashSet<>();

    private Long id;

    @NotEmpty(message = "Имя не может быть пустым")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания 200 символов")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Positive
    private Long duration;

    private Rating rating;

    List<Genre> genres;

    public Film(String name, String description, LocalDate releaseDate, Long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        }

    public Film(Long id, String name, String description, LocalDate releaseDate, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(String name, String description, LocalDate releaseDate, Long duration, Integer rate,
                Rating rating, List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rating = rating;
        if (genres == null) {
            this.genres = new ArrayList<>();
        } else {
            this.genres = genres;
        }
    }

//    private Set<Long> likes = new HashSet<>();
//
//    public Integer getQuantityLikes() {
//        return likes.size();
//    }
}
