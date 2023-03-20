package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void shouldThrowValidationExceptionIfFilmCreateWithAlreadyExistName() {
        FilmController filmController = new FilmController();
        Film film = new Film("Ирония судьбы", "Фильм известного режиссера Рязанова ...", LocalDate.of(1975,01,01), 184);
        filmController.createFilm(film);
        Film filmNew = new Film("Ирония судьбы", "Описание другого фильма ", LocalDate.of(2005,12,25), 100);
        Assertions.assertThrows(ValidationException.class, () -> filmController.createFilm(filmNew));
    }

    @Test
    void shouldThrowValidationExceptionIfUserUpdateWithWrongId() {
        FilmController filmController = new FilmController();
        Film film = new Film("Ирония судьбы", "Фильм известного режиссера Рязанова ...", LocalDate.of(1975,01,01), 184);
        filmController.createFilm(film);
        Film filmUpdate = new Film("Ирония судьбы - 2", "Описание другого фильма", LocalDate.of(1985,01,01), 184);
        Integer idWrong = film.getId() +1;
        filmUpdate.setId(idWrong);
//        Assertions.assertThrows(ValidationException.class, () -> filmController.updateFilm(filmUpdate));
    }
}