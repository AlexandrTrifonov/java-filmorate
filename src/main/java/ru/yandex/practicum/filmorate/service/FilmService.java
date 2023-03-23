package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService implements Comparator<Film> {

    private final FilmStorage filmStorage;
    private final UserService userService;

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
        final User user = userService.getUserById(userId);
        final Film film = getFilmById(filmId);
        film.getLikesFilm().add(Long.valueOf(userId));
        log.info("Лайк фильму {} добавил клиент с id {}", film.getName(), user.getId());
        return getFilmById(filmId);
    }
    public Film deleteLikeFilm(Integer filmId, Integer userId) {
        final User user = userService.getUserById(userId);
        final Film film = getFilmById(filmId);
        for (Long userIdLiked : film.getLikesFilm()) {
            if (userIdLiked == Long.valueOf(userId)) {
                film.getLikesFilm().remove(Long.valueOf(userId));
                log.info("Лайк фильму {} удалил клиент с id {}", film.getName(), user.getId());
                return getFilmById(filmId);
            }
        }
        return film;
/*        log.warn("Лайк пользователя id={} отсутсвует.", userId);
        throw new NotFoundException(String.format("Лайк пользователя id=%s отсутствует.", userId));*/

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
