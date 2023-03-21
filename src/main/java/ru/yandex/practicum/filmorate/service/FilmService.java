package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.validations.ValidateFilm.validateFilm;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService implements Comparator<Film> {

    private final FilmStorage filmStorage;

/*    @Autowired
    FilmStorage filmStorage;*/

//    private Set<Long> likes = new HashSet<>();

    public Collection<Film> findAllFilms() {
        return filmStorage.getFilms().values();
    }
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public void deleteFilm(Film film) {
        filmStorage.deleteFilm(film);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilms().get(id);
    }

    public Film addLikeFilm(Integer filmId, Integer userId) {
        getFilmById(filmId).getLikesFilm().add(Long.valueOf(userId));
        log.info("Лайк фильму {} добавил клиент с id {}", getFilmById(filmId).getName(), userId);
        return getFilmById(filmId);
    }
    public Film deleteLikeFilm(Integer filmId, Integer userId) {
        getFilmById(filmId).getLikesFilm().remove(Long.valueOf(userId));
        log.info("Лайк фильму {} удалил клиент с id {}", getFilmById(filmId).getName(), userId);
        return getFilmById(filmId);
    }
    public Collection<Film> getPopularFilms(Integer count) {
        return filmStorage.getFilms().values().stream()
                .sorted((f0, f1) -> compare(f0, f1))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public int compare(Film f0, Film f1) {
        return f1.getLikesFilm().size()-(f0.getLikesFilm().size());
    }

    /*    private static Integer getNextId() {
        return globalId++;
    }*/
}
