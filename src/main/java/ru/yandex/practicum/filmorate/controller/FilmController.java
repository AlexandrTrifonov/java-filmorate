package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

import static ru.yandex.practicum.filmorate.validations.ValidateFilm.validateFilm;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validateFilm(film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validateFilm(film);
        return filmService.updateFilm(film);
    }
    @DeleteMapping
    public void deleteFilm(@RequestBody Film film) {
        filmService.deleteFilm(film);
    }
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable (name = "id", required = false) Integer id) {
        return filmService.getFilmById(id);
    }
    @PutMapping("/{id}/like/{userId}")
    public Film addLikeFilm(@PathVariable (name = "id", required = false) Integer filmId, @PathVariable (required = false) Integer userId) {
        return filmService.addLikeFilm(filmId, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLikeFilm(@PathVariable (required = false) Integer id, @PathVariable (required = false) Integer userId) {
        return filmService.deleteLikeFilm(id, userId);
    }
    @GetMapping("/popular")
    @ResponseBody
    public Collection<Film> getPopularFilms(@RequestParam (name = "count", required = false, defaultValue = "10") Integer count) {
        return filmService.getPopularFilms(count);
    }
}
