package ru.yandex.practicum.filmorate.validations;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class ValidateFilm {

    public void validateFilm (Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.info("Поле название фильма не заполнено");
            throw new ValidationException("Ошибка валидации");
        }

        if (film.getDescription().length() > 200) {
            log.info("Поле описание длина более 200 символов");
            throw new ValidationException("Ошибка валидации");
        }

        LocalDate earlyDate = LocalDate.of(1895, 12,28);
        if (film.getReleaseDate().isBefore(earlyDate)) {
            log.info("Дата релиза ранее 28 декабря 1895 года");
            throw new ValidationException("Ошибка валидации");
        }

        if (film.getDuration() <= 0) {
            log.info("Поле продолжительность фильма не положительная");
            throw new ValidationException("Ошибка валидации");
        }
    }
}
