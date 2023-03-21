package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.validations.ValidateFilm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validations.ValidateFilm.validateFilm;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/films")
public class FilmController {

//    private Integer idFilm = 1;
//    private final Map<Integer, Film> films = new HashMap<>();

/*    @Autowired
    FilmService filmService;*/
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validateFilm(film);
/*        String name = film.getName();
        for (Film filmName : films.values()) {
            if (filmName.getName().equals(name)) {
                log.warn("Фильм с названием {} уже добавлен", name);
                throw new ValidationException("Фильм с таким названием уже добавлен");
            }
        }*/
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        validateFilm(film);
    /*    if (!films.containsKey(film.getId())) {
            log.warn("Фильм с id={} не существует.", film.getId());
            throw new ValidationException("Фильм с введеным id не существует");
        }
        films.put(film.getId(), film);
        log.info("Обновлен фильм: '{}'", film);*/
        return filmService.updateFilm(film);
    }
    @DeleteMapping
    public void deleteFilm(@RequestBody Film film) {
        filmService.deleteFilm(film);
    }
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable (name = "id", required = false) Integer id) {
        System.out.println("Получение по id");
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
