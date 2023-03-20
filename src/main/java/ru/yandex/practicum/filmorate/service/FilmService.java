package ru.yandex.practicum.filmorate.service;

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
public class FilmService implements Comparator<Film> {

    @Autowired
    FilmStorage filmStorage;

    private Set<Long> likes = new HashSet<>();

    public Collection<Film> findAllFilms() {
        return filmStorage.getFilms().values();
    }
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

/*    public Collection<Film> findTenMostPopularFilms() {
        filmStorage.createFilm()
        System.out.println("10 популярных фильмов");
//        log.info("Получен запрос на получение списка фильмов");
        return films.values();
    }*/

    public void deleteFilm(Film film) {
        filmStorage.deleteFilm(film);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilms().get(id);
    }

    public Film putLikeFilm(Integer id, Integer userId) {
        getFilmById(id).getLikesFilm().add(Long.valueOf(userId));
    //    log.info("Лайк фильму {} добавил клиент с id {}", likedFilm.getName(), userId);
        return getFilmById(id);
    }
    public Film deleteLikeFilm(Integer id, Integer userId) {
        getFilmById(id).getLikesFilm().remove(Long.valueOf(userId));
        //    log.info("Лайк фильму {} добавил клиент с id {}", likedFilm.getName(), userId);
        return getFilmById(id);
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
