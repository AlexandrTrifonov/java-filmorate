package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.LikeFilmStorage;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier
public class LikeFilmDbStorage implements LikeFilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLikeFilm(Integer filmId, Integer userId) {
        String query = "INSERT INTO likes_film (film_id, user_id)" +
                "VALUES (?,?)";
        jdbcTemplate.update(query, filmId, userId);
        log.info("Фильму с id={} добавлен Лайк пользователя с id={}", filmId, userId);
    }

    @Override
    public void deleteLikeFilm(Integer filmId, Integer userId) {
        String query = "DELETE FROM likes_film WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(query, filmId, userId);
        log.info("У фильма с id={} удален Лайк пользователя с id={}", filmId, userId);
    }

}
