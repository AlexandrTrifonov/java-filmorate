package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.MakeFilm;
import ru.yandex.practicum.filmorate.storage.dao.MakeGenre;
import ru.yandex.practicum.filmorate.storage.dao.MakeMpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        try {
            String sqlQuery = "INSERT INTO films (name, description, release_date, duration, mpa_id) " +
                    "values (?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
                stmt.setInt(4, film.getDuration());
                if (film.getMpa() != null) {
                    stmt.setInt(5, film.getMpa().getId());
                }
                return stmt;
            }, keyHolder);

            Integer filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();

            if (film.getGenres() != null) {
                String sqlGenres = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
                for (Genre genre : film.getGenres()) {
                    jdbcTemplate.update(sqlGenres, filmId, genre.getId());
                }
            }
            log.info("Фильм {} добавлен, id={}", film.getName(), filmId);
            return getFilmById(filmId);
        } catch (DataAccessException e) {
            log.warn("Ошибка добавления фильма {}, id={}", film.getName(), film.getId());
            return null;
        }
    }

    @Override
    public Film updateFilm(Film film) {
        try {
            String sqlQuery = "UPDATE FILMS SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                    "WHERE FILM_ID = ?";
            jdbcTemplate.update(
                    sqlQuery,
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getMpa().getId(),
                    film.getId()
            );

            if (film.getGenres() != null) {
                String queryDelete = "DELETE FROM FILM_GENRE WHERE film_id = ?";
                jdbcTemplate.update(queryDelete, film.getId());

                String sqlGenres = "INSERT INTO FILM_GENRE (film_id, genre_id) VALUES (?, ?)";
                for (Genre genre : film.getGenres()) {
                    jdbcTemplate.update(sqlGenres, film.getId(), genre.getId());
                }
            }

            if (film.getLikesFilm() != null) {
                String sqlLikesFilm = "UPDATE likes_film SET film_id = ?, user_id = ? VALUES (?, ?)";
                for (Integer likesFilm : film.getLikesFilm()) {
                    jdbcTemplate.update(sqlLikesFilm, film.getId(), likesFilm);
                }
            }

            log.info("Фильм {} обновлен, id={}", film.getName(), film.getId());
            return getFilmById(film.getId());
        } catch (DataAccessException e) {
            log.warn("Ошибка обновления фильма {}, id={}", film.getName(), film.getId());
            return null;
        }
    }

    @Override
    public void deleteFilm(Film film) {
        try {
            String sqlQuery = "DELETE FROM films WHERE film_id = ?";
            jdbcTemplate.update(sqlQuery, film.getId());
            String sqlQuery1 = "DELETE FROM film_genre WHERE film_id = ?";
            jdbcTemplate.update(sqlQuery1, film.getId());
            String sqlQuery2 = "DELETE FROM likes_film WHERE film_id = ?";
            jdbcTemplate.update(sqlQuery2, film.getId());
            log.info("Фильм {} удален", film.getName());
        } catch (DataAccessException e) {
            log.warn("Ошибка удаления фильма {}, id={}", film.getName(), film.getId());
        }
    }

    @Override
    public Collection<Film> findAllFilms() {
        String sqlQuery = "select * from films";
        List<Film> films = jdbcTemplate.query(sqlQuery, new MakeFilm());
        addMpaNameToFilms(films);
        addLikesToFilms(films);
        addGenresToFilms(films);
        log.info("Получен список фильмов");
        return films;
    }

    private void addMpaNameToFilms(List<Film> films) {
        for (Film film : films) {
            String query = "select * from mpa where mpa_id IN (select mpa_id from films where film_id = ?)";
            Mpa mpa = jdbcTemplate.queryForObject(query, new MakeMpa(), film.getId());
            film.setMpa(mpa);
        }
        log.info("Добавлены лайки с списку фильмов");
    }

    private void addGenresToFilms(List<Film> films) {
        for (Film film : films) {
            String query = "select * from genre where genre_id IN (select genre_id from film_genre where film_id = ?)";
            List<Genre> genre = jdbcTemplate.query(query, new MakeGenre(), film.getId());

            Set<Genre> set = new HashSet<>(genre);
            film.setGenres(set);
        }
        log.info("Добавлены жанры к списку фильмов");
    }

    private void addLikesToFilms(List<Film> films) {
        for (Film film : films) {
            String queryLikesFilm = "select user_id from likes_film where film_id = ?";
            List<Integer> usersId = jdbcTemplate.queryForList(queryLikesFilm, Integer.class, film.getId());
            Set<Integer> likesFilm = new HashSet<>(usersId);
            film.setLikesFilm(likesFilm);
        }
        log.info("Добавлены лайки с списку фильмов");
    }

    @Override
    public Film getFilmById(Integer id) {
        try {
            String query = "select * from films where film_id = ?";
            Film film = jdbcTemplate.queryForObject(query, new MakeFilm(), id);

            String queryMpa = "select * from mpa where mpa_id IN (select mpa_id from films where film_id = ?)";
            Mpa mpa = jdbcTemplate.queryForObject(queryMpa, new MakeMpa(), id);
            assert film != null;
            film.setMpa(mpa);

            String queryGenres = "select * from genre where genre_id IN (select genre_id from film_genre where film_id = ?)";
            List<Genre> genres = jdbcTemplate.query(queryGenres, new MakeGenre(), id);

            Set<Genre> set = new HashSet<>(genres);
            film.setGenres(set);

            String queryLikesFilm = "select user_id from likes_film where film_id = ?";
            List<Integer> usersId = jdbcTemplate.queryForList(queryLikesFilm, Integer.class, id);
            Set<Integer> likesFilm = new HashSet<>(usersId);
            film.setLikesFilm(likesFilm);

            log.info("Фильм {} получен по id={}", film.getName(), id);
            return film;
        } catch (DataAccessException e) {
            log.warn("Ошибка получения фильма по id");
            return null;
        }
    }

    @Override
    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> films = this.findAllFilms();
        return films.stream()
                .sorted((f0, f1) -> f1.getLikesFilm().size() - f0.getLikesFilm().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
