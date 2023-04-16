package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreStorage;
import ru.yandex.practicum.filmorate.storage.dao.MakeGenre;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Genre> getGenres() {
        String sqlQuery = "select * from genre order by genre_id";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, new MakeGenre());
        log.info("Получен список жанров");
        return genres;
    }

    @Override
    public Genre getGenreById(Integer id) {
        try {
            String query = "select * from genre where genre_id = ?";
            log.info("Получен жанр по id={}", id);
            return jdbcTemplate.queryForObject(query, new MakeGenre(), id);
        } catch (DataAccessException e) {
            log.warn("Ошибка жанра");
            return null;
        }
    }
}
