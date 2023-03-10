package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validations.ValidateFilm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private Integer idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAllFilms() {
        log.info("Получен запрос на получение списка фильмов");
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        new ValidateFilm().validateFilm(film);
        film.setId(idFilm);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: '{}'", film);
        this.idFilm ++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        new ValidateFilm().validateFilm(film);
        if (!films.containsKey(film.getId())) {
            log.info("Неверный ключ");
            throw new ValidationException("Неверный ключ");
        }
        films.put(film.getId(), film);
        log.info("Обновлен фильм: '{}'", film);
        return film;
    }
}
