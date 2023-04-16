package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface FilmStorage {

    Film createFilm(Film film);
    Film updateFilm(Film film);
    void deleteFilm(Film film);
    Collection<Film> findAllFilms();
    Film getFilmById(Integer id);

    Collection<Film> getPopularFilms(Integer count);

}
