package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidateServiceTest {
    private final ValidationService validationService = new ValidateService();

    @Test
    public void theNameCannotBeEmpty() throws IOException {
        Film film = new Film();
        film.setDescription("test");
        film.setDuration(Duration.ofMinutes(15));
        film.setReleaseDate(LocalDate.now());
        film.setId((long) 1);
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(film));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(film));
    }

    @Test
    public void theMaximumLengthOfTheDescriptionIs200Characters() throws IOException {
        Film film = new Film();
        film.setName("test");
        film.setDescription("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest" +
                "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        film.setDuration(Duration.ofMinutes(15));
        film.setReleaseDate(LocalDate.now());
        film.setId((long) 1);
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(film));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(film));
    }

    @Test
    public void theReleaseDateIsNotEarlierThan12_28_1895() throws IOException {
        Film film = new Film();
        film.setName("test");
        film.setDescription("test");
        film.setDuration(Duration.ofMinutes(15));
        film.setReleaseDate(LocalDate.of(1800, 12, 21));
        film.setId((long) 1);
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(film));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(film));
    }

    @Test
    public void theDurationOfTheMovieMustBeAPositiveNumber() throws IOException {
        Film film = new Film();
        film.setName("test");
        film.setDescription("test");
        film.setDuration(Duration.ofMinutes(-15));
        film.setReleaseDate(LocalDate.of(1800, 12, 21));
        film.setId((long) 1);
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(film));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(film));
    }

    @Test
    public void anEmailCannotBeEmpty() throws IOException {
        User user = new User();
        user.setId((long) 1);
        user.setName("test");
        user.setLogin("test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(user));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(user));
    }

    @Test
    public void theEmailMustContainTheSymbolDog() throws IOException {
        User user = new User();
        user.setEmail("test");
        user.setId((long) 1);
        user.setName("test");
        user.setLogin("test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(user));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(user));
    }

    @Test
    public void theLoginCannotBeEmpty() throws IOException {
        User user = new User();
        user.setEmail("test");
        user.setId((long) 1);
        user.setName("test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(user));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(user));
    }

    @Test
    public void theLoginCannotContainSpaces() throws IOException {
        User user = new User();
        user.setEmail("test");
        user.setId((long) 1);
        user.setName("test");
        user.setLogin("test test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(user));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(user));
    }

    @Test
    public void theNameToDisplayMayBeEmpty() throws IOException {
        UserRepository repository = new UserRepository();
        User user = new User();
        user.setEmail("test");
        user.setId((long) 1);
        user.setLogin("test@test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        repository.save(user);
        assertEquals(repository.getById(user.getId()).getName(), "test@test");
    }

    @Test
    public void theDateOfBirthCannotBeInTheFuture() throws IOException {
        User user = new User();
        user.setEmail("test");
        user.setId((long) 1);
        user.setLogin("test@test");
        user.setBirthday(LocalDate.of(2025, 1, 1));
        assertThrows(RuntimeException.class, () -> validationService.validateCreate(user));
        assertThrows(RuntimeException.class, () -> validationService.validateUpdate(user));
    }
}