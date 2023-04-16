package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.impl.*;

import java.util.*;

import static ru.yandex.practicum.filmorate.service.ValidateFilm.validateFilm;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final MpaDbStorage mpaDbStorage;
    private final GenreDbStorage genreDbStorage;
    private final LikeFilmDbStorage likeFilmDbStorage;

    public Film createFilm(Film film) {
        validateFilm(film);
        return filmDbStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        Film filmCheck = filmDbStorage.getFilmById(film.getId());
        if (filmCheck == null) {
            log.warn("Ошибка обновления фильма с id={}", film.getId());
            NotFoundException.throwException("Ошибка обновления фильма", film.getId());
        }
        validateFilm(film);
        return filmDbStorage.updateFilm(film);
    }

    public void deleteFilm(Film film) {
        Film filmCheck = filmDbStorage.getFilmById(film.getId());
        if (filmCheck == null) {
            log.warn("Ошибка удаления фильма с id={}", film.getId());
            NotFoundException.throwException("Ошибка удаления фильма", film.getId());
        }
        filmDbStorage.deleteFilm(film);
    }

    public Collection<Film> findAllFilms () {
        return filmDbStorage.findAllFilms();
    }

    public Film getFilmById(Integer id) {
        Film filmCheck = filmDbStorage.getFilmById(id);
        if (filmCheck == null) {
            log.warn("Ошибка получения фильма с id={}", id);
            NotFoundException.throwException("Ошибка получения фильма", id);
        }
        return filmDbStorage.getFilmById(id);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmDbStorage.getPopularFilms(count);
    }
    public void addLikeFilm(Integer filmId, Integer userId) {
        Film filmCheck = filmDbStorage.getFilmById(filmId);
        if (filmCheck == null) {
            log.warn("Ошибка добавления фильму с id={} лайка от пользователя с id={}", filmId, userId);
            NotFoundException.throwException("Ошибка добавления фильму лайка", filmId);
        }
        User userCheck = userDbStorage.getUserById(userId);
        if (userCheck == null) {
            log.warn("Ошибка удаления у фильма с id={} лайка от пользователя с id={}", filmId, userId);
            NotFoundException.throwException("Ошибка удаления у фильма лайка от пользователя", filmId);
        }
        likeFilmDbStorage.addLikeFilm(filmId, userId);
    }

    public void deleteLikeFilm(Integer filmId, Integer userId) {
        Film filmCheck = filmDbStorage.getFilmById(filmId);
        if (filmCheck == null) {
            log.warn("Ошибка удаления у фильма с id={} лайка от пользователя с id={}", filmId, userId);
            NotFoundException.throwException("Ошибка удаления у фильма лайка от пользователя", filmId);
        }
        User userCheck = userDbStorage.getUserById(userId);
        if (userCheck == null) {
            log.warn("Ошибка удаления у фильма с id={} лайка от пользователя с id={}", filmId, userId);
            NotFoundException.throwException("Ошибка удаления у фильма лайка от пользователя", filmId);
        }
        likeFilmDbStorage.deleteLikeFilm(filmId, userId);
    }

    public Collection<Genre> getGenres() {
        return genreDbStorage.getGenres();
    }

    public Genre getGenreById(Integer id) {
        Genre genre = genreDbStorage.getGenreById(id);
        if (genre == null) {
            log.warn("Ошибка жанра");
            NotFoundException.throwException("Ошибка жанра", id);
        }
        return genre;
    }

    public Collection<Mpa> getMpa() {
        return mpaDbStorage.getMpa();
    }

    public Mpa getMpaById(Integer id) {
        Mpa mpa = mpaDbStorage.getMpaById(id);
        if (mpa == null) {
            log.warn("Ошибка Mpa");
            NotFoundException.throwException("Ошибка Mpa", id);
        }
        return mpa;
    }
}
