package ru.yandex.practicum.filmorate.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import static ru.yandex.practicum.filmorate.service.ValidateFilm.validateFilm;

class ValidateFilmTest {

    @Test
    void shouldThrowValidateExceptionIfFilmNameIsBlank() {
        Film film = Film.builder()
                .id(1)
                .name("Ирония судьбы")
                .description("Фильм известного режиссера Рязанова ..")
                .releaseDate(LocalDate.of(1975,01,01))
                .duration(184)
                .build();
        film.setName(" ");
        Assertions.assertThrows(ValidationException.class, () -> validateFilm(film));
    }

    @Test
    void shouldThrowValidateExceptionIfFilmDescriptionOver200() {
        Film film = Film.builder()
                .id(1)
                .name("Ирония судьбы")
                .description("Фильм известного режиссера Рязанова ..")
                .releaseDate(LocalDate.of(1975,01,01))
                .duration(184)
                .build();
        film.setDescription("012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        Assertions.assertThrows(ValidationException.class, () -> validateFilm(film));
    }

    @Test
    void shouldThrowValidateExceptionIfFilmReleaseDateBefore18951228() {
        Film film = Film.builder()
                .id(1)
                .name("Ирония судьбы")
                .description("Фильм известного режиссера Рязанова ..")
                .releaseDate(LocalDate.of(1975,01,01))
                .duration(184)
                .build();
        film.setReleaseDate(LocalDate.of(1777,10,10));
        Assertions.assertThrows(ValidationException.class, () -> validateFilm(film));
    }

    @Test
    void shouldThrowValidateExceptionIfFilmDurationNegativeValue() {
        Film film = Film.builder()
                .id(1)
                .name("Ирония судьбы")
                .description("Фильм известного режиссера Рязанова ..")
                .releaseDate(LocalDate.of(1975,01,01))
                .duration(184)
                .build();
        film.setDuration(0);
        Assertions.assertThrows(ValidationException.class, () -> validateFilm(film));
        film.setDuration(-100);
        Assertions.assertThrows(ValidationException.class, () -> validateFilm(film));
    }
}