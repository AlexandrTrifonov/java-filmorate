package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService implements Comparator<Film> {

    private final FilmStorage filmStorage;

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
        if (!filmStorage.getFilms().containsKey(id)) {
            log.warn("Фильм id={} отсутсвует.", id);
            throw new NotFoundException(String.format("Фильм с id=%s отсутствует.", id));
        }
        return filmStorage.getFilms().get(id);
    }

    public Film addLikeFilm(Integer filmId, Integer userId) {
        if (!filmStorage.getFilms().containsKey(filmId)) {
            log.warn("Фильм id={} отсутсвует.", filmId);
            throw new NotFoundException(String.format("Фильм с id=%s отсутствует.", filmId));
        }
        getFilmById(filmId).getLikesFilm().add(Long.valueOf(userId));
        log.info("Лайк фильму {} добавил клиент с id {}", getFilmById(filmId).getName(), userId);
        return getFilmById(filmId);
    }
    public Film deleteLikeFilm(Integer filmId, Integer userId) {
        if (!filmStorage.getFilms().containsKey(filmId)) {
            log.warn("Фильм id={} отсутсвует.", filmId);
            throw new NotFoundException(String.format("Фильм с id=%s отсутствует.", filmId));
        }
        for (Long userIdLiked : getFilmById(filmId).getLikesFilm()) {
            if (userIdLiked == Long.valueOf(userId)) {
                getFilmById(filmId).getLikesFilm().remove(Long.valueOf(userId));
                log.info("Лайк фильму {} удалил клиент с id {}", getFilmById(filmId).getName(), userId);
                return getFilmById(filmId);
            }
        }
        log.warn("Лайк пользователя id={} отсутсвует.", userId);
        throw new NotFoundException(String.format("Лайк пользователя id=%s отсутствует.", userId));

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
}
