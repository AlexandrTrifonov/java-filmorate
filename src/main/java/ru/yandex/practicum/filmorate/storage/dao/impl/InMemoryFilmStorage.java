package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private Integer idFilm = 1;

    @Getter
    private final Map<Integer, Film> films;

    @Override
    public Film createFilm(Film film) {
        String name = film.getName();
        for (Film filmName : films.values()) {
            if (filmName.getName().equals(name)) {
                log.warn("Фильм с названием {} уже добавлен.", name);
                throw new ValidationException(String.format("Фильм с названием \"%s\" уже добавлен.", name));
            }
        }
        film.setId(idFilm);
        films.put(film.getId(), film);
        log.info("Добавлен фильм: '{}'", film);
        this.idFilm ++;
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с id={} не существует.", film.getId());
            throw new NotFoundException(String.format("Фильм с id=\"%s\" не существует.", film.getId()));
    //        throw new ValidationException("Фильм с введеным id не существует");
        }
        films.put(film.getId(), film);
        log.info("Обновлен фильм: '{}'", film);
        films.put(film.getId(), film);
        log.info("Обновлен фильм: '{}'", film);
        return film;
    }

    @Override
    public void deleteFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Удалить фильм не удалось. Фильм с id {} отсутствует", film.getId());
            throw new NotFoundException(String.format("Фильм с id=\"%s\" не существует.", film.getId()));
        }
        for (Film filmName : films.values()) {
            if (filmName.getId().equals(film.getId())) {
                films.remove(filmName.getId());
                log.info("Удален фильм: '{}'", film.getName());
                break;
            }
        }
    }

    @Override
    public Film getFilmById(Integer id) {
        return null;
    }

    @Override
    public Collection<Film> getPopularFilms(Integer count) {
        return null;
    }

    @Override
    public Collection<Film> findAllFilms() {
        return null;
    }
}
