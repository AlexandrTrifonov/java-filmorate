package ru.yandex.practicum.filmorate.validations;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class ValidateFilm {

    public static void validateFilm (Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Поле название фильма не заполнено");
            throw new ValidationException("Поле название фильма не заполнено");
        }

        if (film.getDescription().length() > 200) {
            log.warn("Поле описание длина более 200 символов");
            throw new ValidationException("Поле описание длина более 200 символов");
        }

        LocalDate earlyDate = LocalDate.of(1895, 12,28);
        if (film.getReleaseDate().isBefore(earlyDate)) {
            log.warn("Дата релиза ранее 28 декабря 1895 года");
            throw new ValidationException("Дата релиза ранее 28 декабря 1895 года");
        }

        if (film.getDuration() <= 0) {
            log.warn("Поле продолжительность фильма не положительная");
            throw new ValidationException("Поле продолжительность фильма не положительная");
        }
    }
}
