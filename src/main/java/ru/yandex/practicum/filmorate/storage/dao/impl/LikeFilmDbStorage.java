package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.LikeFilmStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

/*    @Override
    public Set<Integer> userLikeByFilmId(Integer filmId) {
        String query = "SELECT user_id FROM likes_film WHERE film_id = ?";
        List<Integer> usersId = jdbcTemplate.queryForList(query, Integer.class, filmId);
        Set<Integer> likesFilm = new HashSet<>(usersId);
        log.info("Найдены все Like для фильма id '{}'", filmId);
        return likesFilm;
    }*/
}
